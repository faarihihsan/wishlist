package com.mfaarihihsan.wishlist.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponsePayload {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("price")
    private Integer price;

    @JsonProperty("discountPercentage")
    private Float discountPercentage;

    @JsonProperty("rating")
    private Float rating;

    @JsonProperty("stock")
    private Integer stock;

    @JsonProperty("brand")
    private String brand;

    @JsonProperty("category")
    private String category;

    @JsonProperty("thumbnail")
    private String thumbnail;

    @JsonProperty("images")
    private String[] images;
}
