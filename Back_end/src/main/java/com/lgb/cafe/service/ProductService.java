package com.lgb.cafe.service;

import com.lgb.cafe.payload.dto.ProductDTO;
import org.springframework.http.ResponseEntity;

public interface ProductService {

    ResponseEntity<String> addNewProduct(ProductDTO productDTO);

    ResponseEntity<?> getAllProduct();

    ResponseEntity<?> updateProduct(ProductDTO productDTO);

    ResponseEntity<?> deleteProduct(Integer id);

    ResponseEntity<?> updateStatus(ProductDTO productDTO);

    ResponseEntity<?> getProductByCategory(Integer id);

    ResponseEntity<?> getProductById(Integer id);
}
