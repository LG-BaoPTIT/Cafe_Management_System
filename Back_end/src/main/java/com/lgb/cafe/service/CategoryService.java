package com.lgb.cafe.service;

import com.lgb.cafe.payload.dto.CategoryDTO;
import org.springframework.http.ResponseEntity;

public interface CategoryService {
    ResponseEntity<String> addNewCategory(CategoryDTO categoryDTO);


    ResponseEntity<?> getAllCategory(String filterValue);

    ResponseEntity<String> updateCategory(CategoryDTO categoryDTO);
}
