package com.lgb.cafe.service;

import com.lgb.cafe.payload.dto.UserDTO;
import com.lgb.cafe.entities.User;
import com.lgb.cafe.payload.request.ChangePasswordRequest;
import com.lgb.cafe.payload.request.ForgotPasswordRequest;
import com.lgb.cafe.payload.request.LoginRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    ResponseEntity<String> signup(UserDTO userDTO);

    ResponseEntity<String> login(LoginRequest loginRequest);

    ResponseEntity<List<UserDTO>> getAllUser();

    ResponseEntity<String> update(UserDTO userDTO);

    ResponseEntity<String> checkToken();

    ResponseEntity<String> changePassword(ChangePasswordRequest userDTO);

    User getUserByUsername(String username);

    ResponseEntity<String> forgotPassword(ForgotPasswordRequest request);
}
