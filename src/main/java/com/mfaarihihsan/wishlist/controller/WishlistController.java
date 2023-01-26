package com.mfaarihihsan.wishlist.controller;

import com.mfaarihihsan.wishlist.payload.internal.GetWishlist;
import com.mfaarihihsan.wishlist.payload.request.WishlistRequestPayload;
import com.mfaarihihsan.wishlist.payload.response.GetTopNthResponsePayload;
import com.mfaarihihsan.wishlist.payload.response.ProductResponsePayload;
import com.mfaarihihsan.wishlist.service.WishlistService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
public class WishlistController {
    private final WishlistService wishlistService;

    @GetMapping("/categories")
    public ResponseEntity getAllCategories() {
        try {
            List<String> result = wishlistService.getAllCategories();
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/products")
    public ResponseEntity getAllProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String name
    ) {
        try {
            if (!checkGetProductParam(category, name))
                return ResponseEntity.badRequest().body("Request cannot have multiple param. Choose one between name and category!");

            List<ProductResponsePayload> result;

            if (category != null) {
                result = wishlistService.getAllProductByCategory(category);
            } else if (name != null) {
                result = wishlistService.getAllProductByProductName(name);
            } else {
                result = wishlistService.getAllProducts();
            }

            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/wishlist/add")
    public ResponseEntity addWishlist(@RequestBody WishlistRequestPayload wishlistRequestPayload) {
        try {
            if (!checkWishlistRequestPayload(wishlistRequestPayload, 1))
                return ResponseEntity.badRequest().body("Request need value of \"productId\"");

            wishlistService.addWishlist(wishlistRequestPayload);
            return ResponseEntity.ok().body("Success");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/wishlist/done")
    public ResponseEntity doneWishlist(@RequestBody WishlistRequestPayload wishlistRequestPayload) {
        try {
            if (!checkWishlistRequestPayload(wishlistRequestPayload, 2))
                return ResponseEntity.badRequest().body("Request need value of \"wishlistId\"");

            wishlistService.doneWishlist(wishlistRequestPayload);
            return ResponseEntity.ok().body("Success");

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/wishlist/delete")
    public ResponseEntity deleteWishlist(@RequestBody WishlistRequestPayload wishlistRequestPayload) {
        try {
            if (!checkWishlistRequestPayload(wishlistRequestPayload, 2))
                return ResponseEntity.badRequest().body("Request need value of \"wishlistId\"");

            wishlistService.deleteWishlist(wishlistRequestPayload);
            return ResponseEntity.ok().body("Success");

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/wishlist")
    public ResponseEntity getWishlist(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Integer page
    ) {
        if (!checkGetWishlistParam(name, category, status))
            return ResponseEntity.badRequest().body("Request cannot have multiple param. Choose one between name, category, and status!");

        GetWishlist wishlist = new GetWishlist(name, category, status, limit, page);
        return ResponseEntity.ok().body(wishlistService.getWishlist(wishlist));
    }

    @GetMapping("/top-nth-product-per-category")
    public ResponseEntity getTopNthProductPricePerCategory(@RequestParam Integer n) {
        try {
            List<GetTopNthResponsePayload> result = wishlistService.getTopNthPerCategory(n);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    private boolean checkGetProductParam(String category, String name) {
        int totalParam = 0;
        if (category != null) totalParam++;
        if (name != null) totalParam++;

        return totalParam < 2;
    }

    private boolean checkGetWishlistParam(String name, String category, String status) {
        int totalQuery = 0;
        if (name != null) totalQuery++;
        if (category != null) totalQuery++;
        if (status != null) totalQuery++;

        return totalQuery < 2;
    }

    private boolean checkWishlistRequestPayload(WishlistRequestPayload payload, Integer method) {
        if (method == 1) {
            return payload.getProductId() != null;
        } else {
            return payload.getWishlistId() != null;
        }
    }

}
