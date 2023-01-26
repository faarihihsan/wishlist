package com.mfaarihihsan.wishlist.payload.internal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
public class GetWishlist {
    @JsonProperty("name")
    private String name;

    @JsonProperty("category")
    private String category;

    @JsonProperty("status")
    private String status;

    @JsonProperty("limit")
    private Integer limit;

    @JsonProperty("page")
    private Integer page;

    public GetWishlist(String name, String category, String status, Integer limit, Integer page) {
        this.name = name;
        this.category = category;
        this.status = status;
        this.limit = limit == null? 10 : limit;
        this.page = page == null? 0 : page;
    }
}
