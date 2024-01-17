package com.lgb.cafe.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private Integer id;

    private String name;

    private Integer categoryId;

    private String categoryName;

    private String description;

    private Integer price;

    private String status;

}
