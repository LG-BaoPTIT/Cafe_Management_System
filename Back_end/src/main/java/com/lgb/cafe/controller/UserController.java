package com.lgb.cafe.controller;

import com.lgb.cafe.constents.CafeConstants;
import com.lgb.cafe.payload.dto.UserDTO;
import com.lgb.cafe.payload.request.LoginRequest;
import com.lgb.cafe.service.UserService;
import com.lgb.cafe.utils.CafeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody UserDTO userDTO){
        try{
            return userService.signup(userDTO);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest){
        try{
            return userService.login(loginRequest);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
