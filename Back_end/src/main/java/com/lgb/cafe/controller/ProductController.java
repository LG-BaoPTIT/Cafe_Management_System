package com.lgb.cafe.controller;

import com.lgb.cafe.constents.CafeConstants;
import com.lgb.cafe.payload.dto.ProductDTO;
import com.lgb.cafe.service.ProductService;
import com.lgb.cafe.utils.CafeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("/add")
    public ResponseEntity<String> addNewProduct(@RequestBody ProductDTO productDTO){
        try {
            return productService.addNewProduct(productDTO);
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAllProduct(){
        try {
            return productService.getAllProduct();
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("/update")
     public ResponseEntity<?> updateProduct(@RequestBody ProductDTO productDTO){
        try {
            return productService.updateProduct(productDTO);
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id){
        try {
            return productService.deleteProduct(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("/updateStatus")
    public ResponseEntity<?> updateStatus(@RequestBody ProductDTO productDTO){
        try {
            return productService.updateStatus(productDTO);
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @GetMapping("/getByCategory/{id}")
    public ResponseEntity<?> getProductByCategory(@PathVariable Integer id){

        try {
            return productService.getProductByCategory(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Integer id){
        try {
            return productService.getProductById(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }


}
