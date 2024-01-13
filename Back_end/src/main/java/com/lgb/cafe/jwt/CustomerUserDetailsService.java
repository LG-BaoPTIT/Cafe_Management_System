package com.lgb.cafe.jwt;

import com.lgb.cafe.entities.User;
import com.lgb.cafe.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Slf4j
@Service
public class CustomerUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    private User userDetail;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Inside loadUserByUsername {}", username);
        userDetail = userRepository.findByEmail(username);
        if (!Objects.isNull(userDetail)) {
            List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList( userDetail.getRole());
            return new org.springframework.security.core.userdetails.User(
                    userDetail.getEmail(), userDetail.getPassword(), authorities);
        } else {
            throw new UsernameNotFoundException("User not found.");
        }
    }


    public User getUserDetail(){
       // userDetail.setPassword(null);
        return userDetail;
    }
}
