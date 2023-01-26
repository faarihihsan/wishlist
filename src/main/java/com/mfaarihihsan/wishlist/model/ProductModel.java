package com.mfaarihihsan.wishlist.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "price")
    private Integer price;

    @Column(name = "rating")
    private Float rating;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "category_db_id", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private CategoryModel category;

    public ProductModel(String name, Integer productId, Integer price, Float rating, CategoryModel category) {
        this.name = name;
        this.productId = productId;
        this.price = price;
        this.rating = rating;
        this.category = category;
    }
}
