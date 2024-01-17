package com.lgb.cafe.repositories;

import com.lgb.cafe.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    User findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsById(Integer integer);

    @Modifying
    @Transactional
    @Query(value = "UPDATE user SET status = :status WHERE id = :userId",nativeQuery = true)
    void updateStatusById(@Param("userId") Integer userId,@Param("status") String status);

    @Query(value = "SELECT user.email FROM user WHERE role = 'admin'",nativeQuery = true)
    List<String> getAllEmailOfAdmin();

    @Query(value = "SELECT user.email FROM user WHERE id = :id",nativeQuery = true)
    String getEmailById(@Param("id") Integer id);

    User save(User user);

    List<User> findAll();


}


