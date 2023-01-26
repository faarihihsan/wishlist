package com.mfaarihihsan.wishlist.service;

import com.mfaarihihsan.wishlist.model.*;
import com.mfaarihihsan.wishlist.payload.internal.GetWishlist;
import com.mfaarihihsan.wishlist.payload.request.WishlistRequestPayload;
import com.mfaarihihsan.wishlist.payload.response.*;
import com.mfaarihihsan.wishlist.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WishlistServiceImpl implements WishlistService {
    private final Environment env;
    private final ProductDb productDb;
    private final CategoryDb categoryDb;
    private final WishlistDb wishlistDb;

    @Override
    public List<String> getAllCategories() {
        RestTemplate restTemplate = new RestTemplate();
        String uri = Objects.requireNonNull(env.getProperty("product.server")).concat("/products/categories");
        String[] categoryResult = restTemplate.getForObject(uri, String[].class);
        return Arrays.asList(categoryResult);
    }

    private List<ProductResponsePayload> getProductFromRestAPI(String uri) {
        RestTemplate restTemplate = new RestTemplate();
        ProductsResponsePayload productResult = restTemplate.getForObject(uri, ProductsResponsePayload.class);
        return Arrays.asList(productResult.getProductResponsePayloads());
    }

    @Override
    public List<ProductResponsePayload> getAllProductByCategory(String category) {
        String uri = Objects.requireNonNull(env.getProperty("product.server"))
                .concat("/products/category/")
                .concat(category);
        return getProductFromRestAPI(uri);
    }

    @Override
    public List<ProductResponsePayload> getAllProducts() {
        String uri = Objects.requireNonNull(env.getProperty("product.server"))
                .concat("/products?limit=100");
        return getProductFromRestAPI(uri);
    }

    @Override
    public List<ProductResponsePayload> getAllProductByProductName(String name) {
        String uri = Objects.requireNonNull(env.getProperty("product.server"))
                .concat("/products/search?q=")
                .concat(name);
        return getProductFromRestAPI(uri);
    }

    @Override
    public void addWishlist(WishlistRequestPayload wishlistRequestPayload) throws NoSuchElementException {
        RestTemplate restTemplate = new RestTemplate();
        String uri = Objects.requireNonNull(env.getProperty("product.server"))
                .concat("/products/")
                .concat(wishlistRequestPayload.getProductId().toString());
        ProductResponsePayload productResult = restTemplate.getForObject(uri, ProductResponsePayload.class);

        if (productResult.getId() == null) {
            throw new NoSuchElementException("Product id is not valid");
        }

        CategoryModel category = categoryDb.findByName(productResult.getCategory()).orElseGet(() -> new CategoryModel(
                productResult.getCategory()
        ));

        ProductModel product = productDb.findByProductId(productResult.getId()).orElseGet(() -> new ProductModel(
                productResult.getTitle(),
                productResult.getId(),
                productResult.getPrice(),
                productResult.getRating(),
                category
        ));

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        WishlistModel wishlist = new WishlistModel(
                EStatus.WISHLIST,
                product,
                timestamp.toLocalDateTime(),
                timestamp.toLocalDateTime()
        );

        categoryDb.save(category);
        productDb.save(product);
        wishlistDb.save(wishlist);
    }

    @Override
    public void doneWishlist(WishlistRequestPayload wishlistRequestPayload) throws NoSuchElementException {
        updateWishlist(wishlistRequestPayload, EStatus.DONE);
    }

    @Override
    public void deleteWishlist(WishlistRequestPayload wishlistRequestPayload) throws NoSuchElementException {
        updateWishlist(wishlistRequestPayload, EStatus.DELETED);
    }

    private void updateWishlist(WishlistRequestPayload wishlistRequestPayload, EStatus status) throws NoSuchElementException {
        WishlistModel wishlist = wishlistDb.findById(wishlistRequestPayload.getWishlistId()).orElseThrow(
                () -> new NoSuchElementException("Wishlist id is not valid")
        );

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        wishlist.setStatus(status);
        wishlist.setUpdatedAt(timestamp.toLocalDateTime());
        wishlistDb.save(wishlist);
    }

    @Override
    public List<GetWishlistResponsePayload> getWishlist(GetWishlist wishlist) {
        Pageable pageable = PageRequest.of(wishlist.getPage(), wishlist.getLimit());
        List<WishlistModel> wishlistDbOutput;

        if (wishlist.getName() != null) {
            wishlistDbOutput = wishlistDb.getWishlistModelByName(wishlist.getName(), pageable);
        } else if (wishlist.getCategory() != null) {
            wishlistDbOutput = wishlistDb.getWishlistModelByCategory(wishlist.getCategory(), pageable);
        } else if (wishlist.getStatus() != null) {
            String inputStatusStr = wishlist.getStatus().toUpperCase();
            wishlistDbOutput = wishlistDb.getWishlistModelByStatus(getStatusInt(inputStatusStr), pageable);
        } else {
            wishlistDbOutput = wishlistDb.findAll(pageable).getContent();
        }

        List<ProductResponsePayload> productRestList = getAllProducts();
        List<GetWishlistResponsePayload> response = new ArrayList<>();

        response = wishlistDbOutput.stream().map(wishlistModel -> {
            ProductResponsePayload productRest = productRestList.stream()
                    .filter(product -> product.getId().equals(wishlistModel.getProduct().getProductId()))
                    .findFirst()
                    .orElseGet(() -> new ProductResponsePayload());

            GetWishlistResponsePayload enrichedWishlistData = new GetWishlistResponsePayload(
                    wishlistModel.getId(),
                    wishlistModel.getStatus(),
                    productRest,
                    wishlistModel.getCreatedAt(),
                    wishlistModel.getUpdatedAt()
            );
            return enrichedWishlistData;
        }).collect(Collectors.toList());

        return response;
    }

    private Integer getStatusInt(String inputStatusStr) {
        int inputStatusInt = 0;

        if (inputStatusStr.equals("DONE")) inputStatusInt = 1;
        else if (inputStatusStr.equals("DELETED")) inputStatusInt = 2;

        return inputStatusInt;
    }

    @Override
    public List<GetTopNthResponsePayload> getTopNthPerCategory(Integer n) {
        return productDb.getTopNthPerCategory(n);
    }
}
