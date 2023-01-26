package com.mfaarihihsan.wishlist.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WishlistRequestPayload {
    @JsonProperty("productId")
    private Integer productId;

    @JsonProperty("wishlistId")
    private Integer wishlistId;
}
