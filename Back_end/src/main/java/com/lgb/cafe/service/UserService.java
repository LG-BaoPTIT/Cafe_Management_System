package com.lgb.cafe.service;

import com.lgb.cafe.payload.dto.UserDTO;
import com.lgb.cafe.entities.User;
import com.lgb.cafe.payload.request.LoginRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    ResponseEntity<String> signup(UserDTO userDTO);
    ResponseEntity<String> login(LoginRequest loginRequest);
    ResponseEntity<List<UserDTO>> getAllUser();

    User getUserByUsername(String username);

}
