package com.lgb.cafe.payload.dto;

import com.lgb.cafe.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Integer id;

    private String name;

    private CategoryDTO category;

    private String description;

    private Integer price;

    private String status;
}
