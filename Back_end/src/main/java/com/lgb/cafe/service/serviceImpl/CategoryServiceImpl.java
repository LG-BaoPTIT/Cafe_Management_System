package com.lgb.cafe.service.serviceImpl;

import com.google.common.base.Strings;
import com.lgb.cafe.constents.CafeConstants;
import com.lgb.cafe.entities.Category;
import com.lgb.cafe.jwt.JwtFilter;
import com.lgb.cafe.payload.dto.CategoryDTO;
import com.lgb.cafe.repositories.CategoryRepository;
import com.lgb.cafe.service.CategoryService;
import com.lgb.cafe.utils.CafeUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    JwtFilter jwtFilter;
    @Override
    public ResponseEntity<String> addNewCategory(CategoryDTO categoryDTO) {
        try{
            if(!categoryDTO.getName().isEmpty() && !categoryDTO.getName().isBlank()){
                categoryRepository.save(modelMapper.map(categoryDTO, Category.class));
                return CafeUtils.getResponseEntity("Category Added Successfully",HttpStatus.OK);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> getAllCategory(String filterValue) {
        try{
            if(!Strings.isNullOrEmpty(filterValue) && filterValue.equalsIgnoreCase("true")){
                return new ResponseEntity<>(categoryRepository.getCategories(),HttpStatus.OK);

            }
            else {
                return new ResponseEntity<>(categoryRepository.findAll(),HttpStatus.OK);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> updateCategory(CategoryDTO categoryDTO) {
        try{
            if(categoryDTO.getId()==null){
                return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);

            }
            if( categoryRepository.existsById(categoryDTO.getId())){
                categoryRepository.save(modelMapper.map(categoryDTO,Category.class));
                return CafeUtils.getResponseEntity("Update Category Successfully", HttpStatus.OK);

            }
            else {
                return CafeUtils.getResponseEntity("Category id doesn't exists", HttpStatus.OK);

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
