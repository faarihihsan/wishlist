package com.mfaarihihsan.wishlist.service;

import com.mfaarihihsan.wishlist.payload.internal.GetWishlist;
import com.mfaarihihsan.wishlist.payload.request.WishlistRequestPayload;
import com.mfaarihihsan.wishlist.payload.response.GetTopNthResponsePayload;
import com.mfaarihihsan.wishlist.payload.response.GetWishlistResponsePayload;
import com.mfaarihihsan.wishlist.payload.response.ProductResponsePayload;

import java.util.List;
import java.util.NoSuchElementException;

public interface WishlistService {
    public List<String> getAllCategories();
    public List<ProductResponsePayload> getAllProducts();
    public List<ProductResponsePayload> getAllProductByCategory(String category);
    public List<ProductResponsePayload> getAllProductByProductName(String name);
    public void addWishlist(WishlistRequestPayload wishlistRequestPayload) throws NoSuchElementException;
    public void doneWishlist(WishlistRequestPayload wishlistRequestPayload) throws NoSuchElementException;
    public void deleteWishlist(WishlistRequestPayload wishlistRequestPayload) throws NoSuchElementException;
    public List<GetWishlistResponsePayload> getWishlist(GetWishlist wishlist);
    public List<GetTopNthResponsePayload> getTopNthPerCategory(Integer n);

}
