package com.lgb.cafe.service.serviceImpl;

import com.google.common.base.Strings;
import com.lgb.cafe.constents.CafeConstants;
import com.lgb.cafe.jwt.CustomerUserDetailsService;
import com.lgb.cafe.jwt.JwtFilter;
import com.lgb.cafe.jwt.JwtUtil;
import com.lgb.cafe.payload.dto.UserDTO;
import com.lgb.cafe.entities.User;
import com.lgb.cafe.payload.request.ChangePasswordRequest;
import com.lgb.cafe.payload.request.ForgotPasswordRequest;
import com.lgb.cafe.payload.request.LoginRequest;
import com.lgb.cafe.repositories.UserRepository;
import com.lgb.cafe.service.UserService;
import com.lgb.cafe.utils.CafeUtils;
import com.lgb.cafe.utils.EmailUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CustomerUserDetailsService customerUserDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    EmailUtils emailUtils;

    @Override
    public ResponseEntity<String> signup(UserDTO userDTO) {
        log.info("Inside signup {}", userDTO);
        try{
            if(validateSignUp(userDTO)){
                if(userRepository.existsByEmail(userDTO.getEmail())){
                    return CafeUtils.getResponseEntity("Email already exits",HttpStatus.BAD_REQUEST );
                }
                else{
                    userDTO.setStatus("false");
                    userDTO.setRole("user");
                    userRepository.save(modelMapper.map(userDTO,User.class));
                    return CafeUtils.getResponseEntity("Successfully Registered",HttpStatus.OK);

                }
            }
            else{
                return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> login(LoginRequest loginRequest) {
        log.info("Inside login {}",loginRequest);
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
            if(authentication.isAuthenticated()){
                if(customerUserDetailsService.getUserDetail().getStatus().equalsIgnoreCase("true")){
                    return new ResponseEntity<String>("{\"token\":\""+
                            jwtUtil.generateToken(customerUserDetailsService.getUserDetail().getEmail(), customerUserDetailsService.getUserDetail().getRole()) +"\"}" ,HttpStatus.OK);
                }
                else {
                    return new ResponseEntity<String>("{\"message\":\"" +"wait for admin approval."+"\"}",HttpStatus.BAD_REQUEST);
                }
            }
        }catch (Exception e){
            log.error("{}",e);
        }
        return new ResponseEntity<String>("{\"message\":\"" +"Bad Credentials."+"\"}",HttpStatus.BAD_REQUEST);

    }

    @Override
    public ResponseEntity<List<UserDTO>> getAllUser() {
        try{
            List<UserDTO> resultList = userRepository.findAll()
                    .stream()
                    .map(user -> modelMapper.map(user,UserDTO.class))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(resultList,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(UserDTO userDTO) {
        try{
            if(userRepository.existsById(userDTO.getId())){
                userRepository.updateStatusById(userDTO.getId(),userDTO.getStatus());


                new Thread( () -> sendMailToAllAdmin(userDTO.getStatus(),userRepository.getEmailById(userDTO.getId()),userRepository.getAllEmailOfAdmin())).start(); ;
                //sendMailToAllAdmin(userDTO.getStatus(),userRepository.getEmailById(userDTO.getId()),userRepository.getAllEmailOfAdmin());
                return CafeUtils.getResponseEntity("Status updated successfully!",HttpStatus.OK);

            }else {
                return CafeUtils.getResponseEntity("User id doesn't exist.",HttpStatus.OK);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> checkToken() {
        return CafeUtils.getResponseEntity("True",HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> changePassword(ChangePasswordRequest request) {
        try {
            User user = userRepository.findByEmail(jwtFilter.getCurrentUser());
            if(!user.equals(null)){
                if(user.getPassword().equals(request.getOldPassword())){
                    user.setPassword(request.getNewPassword());
                    userRepository.save(user);
                    return CafeUtils.getResponseEntity("Password Update Successfully.",HttpStatus.OK);

                }
                return CafeUtils.getResponseEntity("Incorrect Old PassWord.",HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> forgotPassword(ForgotPasswordRequest request) {
        try{
            User user = userRepository.findByEmail(request.getEmail());
            if (!Objects.isNull(user) && !Strings.isNullOrEmpty(user.getEmail())) {
                emailUtils.forgotPasswordMail(user.getEmail(),"Credentials by Cafe Management System",user.getPassword());
                return CafeUtils.getResponseEntity("Check your mail for credentials",HttpStatus.OK);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private void sendMailToAllAdmin(String status, String user, List<String> allEmailOfAdmin) {
        allEmailOfAdmin.remove(jwtFilter.getCurrentUser());
        if(status.equalsIgnoreCase("true")){
            emailUtils.sendSimpleMessage(user,"Account Approved","USER:- "+user+" \n is approved by \nADMIN:-" + jwtFilter.getCurrentUser(),allEmailOfAdmin);
        }
        if(status.equalsIgnoreCase("false")){
            emailUtils.sendSimpleMessage(user,"Account Disabled","USER:- "+user+" \n is disabled by \nADMIN:-" + jwtFilter.getCurrentUser(),allEmailOfAdmin);
        }

    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByEmail(username);
    }

    private boolean validateSignUp(UserDTO userDTO) {
        if (isNullOrBlank(userDTO.getName())) {
            log.warn("Name cannot be empty");
            return false;
        }

        if (isNullOrBlank(userDTO.getContactNumber())) {
            log.warn("Contact number cannot be empty");
            return false;
        }

        if (isNullOrBlank(userDTO.getEmail())) {
            log.warn("Email cannot be empty");
            return false;
        }

        if (isNullOrBlank(userDTO.getPassword())) {
            log.warn("Password cannot be empty");
            return false;
        }

        return true;
    }

    private boolean isNullOrBlank(String value) {
        return value == null || value.isBlank();
    }
}
