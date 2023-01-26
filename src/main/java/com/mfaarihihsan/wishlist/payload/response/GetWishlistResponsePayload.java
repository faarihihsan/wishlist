package com.mfaarihihsan.wishlist.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mfaarihihsan.wishlist.model.EStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetWishlistResponsePayload {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("status")
    private EStatus status;

    @JsonProperty("product")
    private ProductResponsePayload product;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;
}
