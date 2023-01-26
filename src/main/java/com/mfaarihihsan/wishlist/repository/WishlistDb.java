package com.mfaarihihsan.wishlist.repository;

import com.mfaarihihsan.wishlist.model.WishlistModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WishlistDb extends JpaRepository<WishlistModel, Integer> {
    Optional<WishlistModel> findById(Integer integer);

    @Query(value = "SELECT w.*\n" +
            "FROM wishlist w \n" +
            "JOIN product p ON w.product_db_id = p.id\n" +
            "JOIN category c ON c.id = p.category_db_id\n" +
            "WHERE c.name = :category ;", nativeQuery = true)
    List<WishlistModel> getWishlistModelByCategory(String category, Pageable pageable);

    @Query(value = "SELECT w.*\n" +
            "FROM wishlist w \n" +
            "JOIN product p ON w.product_db_id = p.id\n" +
            "WHERE p.name = :name ;", nativeQuery = true)
    List<WishlistModel> getWishlistModelByName(String name, Pageable pageable);

    @Query(value = "SELECT w.*\n" +
            "FROM wishlist w \n" +
            "WHERE w.status = :status ;", nativeQuery = true)
    List<WishlistModel> getWishlistModelByStatus(Integer status, Pageable pageable);

}
