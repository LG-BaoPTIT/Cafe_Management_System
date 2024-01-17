package com.lgb.cafe.controller;

import com.lgb.cafe.constents.CafeConstants;
import com.lgb.cafe.payload.dto.UserDTO;
import com.lgb.cafe.payload.request.ChangePasswordRequest;
import com.lgb.cafe.payload.request.ForgotPasswordRequest;
import com.lgb.cafe.payload.request.LoginRequest;
import com.lgb.cafe.service.UserService;
import com.lgb.cafe.utils.CafeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @PreAuthorize("hasAuthority('admin')")
    @GetMapping("/get")
    public ResponseEntity<?> getAllUser(){
        try{
            return userService.getAllUser();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody UserDTO userDTO){
        try{
            return userService.update(userDTO);
        }catch(Exception e){
            e.printStackTrace();
        }
        return  CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/checkToken")
    public ResponseEntity<String> checkToken(){
        try{
            return userService.checkToken();
        }catch (Exception e){
            e.printStackTrace();
        }
        return  CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @PostMapping("changePassword")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest){
        try{
            return userService.changePassword(changePasswordRequest);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest request){
        try{
            return userService.forgotPassword(request);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
