package com.mfaarihihsan.wishlist.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductsResponsePayload {
    @JsonProperty("products")
    private ProductResponsePayload[] productResponsePayloads;

    @JsonProperty("total")
    private Integer total;

    @JsonProperty("skip")
    private Integer skip;

    @JsonProperty("limit")
    private Integer limit;
}
