package com.lgb.cafe.repositories;

import com.lgb.cafe.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {

    @Query(value = "SELECT * FROM category c WHERE c.id IN (SELECT p.category_fk FROM product p WHERE p.status = 'true')", nativeQuery = true)
    List<Category> getCategories();

    List<Category> findAll();

    Category save(Category category);
}
