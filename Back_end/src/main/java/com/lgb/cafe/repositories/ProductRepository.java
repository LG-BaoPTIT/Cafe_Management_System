package com.lgb.cafe.repositories;

import com.lgb.cafe.entities.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE product SET status = :status WHERE id = :id",nativeQuery = true)
    void updateProductStatus(@Param("status") String status, @Param("id") Integer id);

    @Query(value = "SELECT * FROM product WHERE category_fk = :id AND status = :status", nativeQuery = true)
    List<Product> getProductsByCategory_Id(@Param("id") Integer id, @Param("status") String status);
}
