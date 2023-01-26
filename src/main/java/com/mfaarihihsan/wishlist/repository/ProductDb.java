package com.mfaarihihsan.wishlist.repository;

import com.mfaarihihsan.wishlist.model.ProductModel;
import com.mfaarihihsan.wishlist.payload.response.GetTopNthResponsePayload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductDb extends JpaRepository<ProductModel, Integer> {
    Optional<ProductModel> findByProductId(Integer productId);

    @Query(value = "SELECT Category, Product, Price, Rating FROM (\n" +
            "    SELECT p.name AS Product,\n" +
            "           p.price AS Price,\n" +
            "           p.rating AS Rating,\n" +
            "           c.name AS Category,\n" +
            "           DENSE_RANK() OVER (PARTITION BY c.name ORDER BY p.price DESC) AS rank\n" +
            "    FROM product p JOIN category c on p.category_db_id = c.id\n" +
            ") t where rank <= :n", nativeQuery = true)
    List<GetTopNthResponsePayload> getTopNthPerCategory(Integer n);
}
