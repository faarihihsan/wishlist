package com.mfaarihihsan.wishlist.repository;

import com.mfaarihihsan.wishlist.model.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryDb extends JpaRepository<CategoryModel, Integer> {
    Optional<CategoryModel> findByName(String name);
}
