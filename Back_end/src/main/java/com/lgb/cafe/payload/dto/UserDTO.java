package com.lgb.cafe.payload.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String name;

    private String contactNumber;

    private String email;

    private String password;

    private String status;

    private String role;
}
