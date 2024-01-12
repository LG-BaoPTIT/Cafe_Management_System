package com.lgb.cafe.repositories;

import com.lgb.cafe.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    User findByEmail(String email);

    boolean existsByEmail(String email);

    User save(User user);


}


