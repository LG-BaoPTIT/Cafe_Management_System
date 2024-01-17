package com.lgb.cafe.controller;

import com.lgb.cafe.constents.CafeConstants;
import com.lgb.cafe.payload.dto.CategoryDTO;
import com.lgb.cafe.service.CategoryService;
import com.lgb.cafe.utils.CafeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("/add")
    public ResponseEntity<String> addNewCategory(@RequestBody CategoryDTO categoryDTO){
        try {
            return categoryService.addNewCategory(categoryDTO);
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAllCategory(@RequestParam(required = false) String filterValue){
        try {
            return categoryService.getAllCategory(filterValue);
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("/update")
    public  ResponseEntity<String> updateCategory(@RequestBody CategoryDTO categoryDTO){
        try {
            return categoryService.updateCategory(categoryDTO);
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

}
