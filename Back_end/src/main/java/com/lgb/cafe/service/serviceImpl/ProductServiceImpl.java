package com.lgb.cafe.service.serviceImpl;

import com.lgb.cafe.constents.CafeConstants;
import com.lgb.cafe.entities.Product;
import com.lgb.cafe.payload.dto.ProductDTO;
import com.lgb.cafe.payload.response.ProductResponse;
import com.lgb.cafe.repositories.ProductRepository;
import com.lgb.cafe.service.ProductService;
import com.lgb.cafe.utils.CafeUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public ResponseEntity<String> addNewProduct(ProductDTO productDTO) {
       try {
           if (productDTO.getName() == null || productDTO.getName().trim().isEmpty() ||
                   productDTO.getPrice() == null ||
                   productDTO.getStatus() == null || productDTO.getStatus().trim().isEmpty()) {
               return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
           }
           productRepository.save(modelMapper.map(productDTO, Product.class));
           return CafeUtils.getResponseEntity("Product added successfully.", HttpStatus.OK);
       }catch (Exception e){
           e.printStackTrace();
           return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }

    @Override
    public ResponseEntity<?> getAllProduct() {
        try {
            List<Product> products = productRepository.findAll();
            List<ProductResponse> result = new ArrayList<>();
            for (Product product : products){
                ProductResponse productResponse = modelMapper.map(product,ProductResponse.class);
                productResponse.setCategoryId(product.getCategory().getId());
                productResponse.setCategoryName(product.getCategory().getName());
                result.add(productResponse);
            }
            return ResponseEntity.ok(result);
        }catch (Exception e){
            e.printStackTrace();
            return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> updateProduct(ProductDTO productDTO) {
        try {
            Optional<Product> optional = productRepository.findById(productDTO.getId());
            if(optional.isPresent()){
                  productRepository.save(modelMapper.map(productDTO, Product.class));
                return CafeUtils.getResponseEntity("Product updated successfully.", HttpStatus.OK);

            }else {
                return CafeUtils.getResponseEntity("Product id does not exist",HttpStatus.OK);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> deleteProduct(Integer id) {
        try {
            if(productRepository.existsById(id)){
                productRepository.deleteById(id);
                return CafeUtils.getResponseEntity("Product Deleted Successfully", HttpStatus.OK);
            }else {
                return CafeUtils.getResponseEntity("Product id does not exist", HttpStatus.OK);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> updateStatus(ProductDTO productDTO) {
        try {
            if(productRepository.existsById(productDTO.getId())){
                if(productDTO.getStatus() == null || productDTO.getStatus().isEmpty() || productDTO.getStatus().isBlank()){
                    return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
                }
                productRepository.updateProductStatus(productDTO.getStatus(), productDTO.getId());
                return CafeUtils.getResponseEntity("Status Of Product Updated Successfully", HttpStatus.OK);
            }else {
                return CafeUtils.getResponseEntity("Product id does not exist", HttpStatus.OK);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> getProductByCategory(Integer id) {
        try {
            if(id != null){
                List<Product> products = productRepository.getProductsByCategory_Id(id,"true");
                List<ProductDTO> result = products.stream()
                        .map(product -> modelMapper.map(product,ProductDTO.class))
                        .collect(Collectors.toList());
               return new ResponseEntity<>(result,HttpStatus.OK);
            }else {
                return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA,HttpStatus.OK);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> getProductById(Integer id) {

        try {
            if(productRepository.existsById(id)){
                Optional<Product> product = productRepository.findById(id);

                return new ResponseEntity<>(modelMapper.map(product.get(),ProductDTO.class),HttpStatus.OK);
            }else {
                return CafeUtils.getResponseEntity("Product id does not exist",HttpStatus.OK);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
