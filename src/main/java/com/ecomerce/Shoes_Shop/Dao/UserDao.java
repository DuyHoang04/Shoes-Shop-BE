package com.ecomerce.Shoes_Shop.Dao;

import com.ecomerce.Shoes_Shop.POJO.User;
import com.ecomerce.Shoes_Shop.Wrapper.UserWrapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserDao extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    User findUserByEmail(String email);

    Page<User> findByEmailContainingIgnoreCase(String email, Pageable pageable);
//    Long countByEmailContainingIgnoreCase(String email);

    List<UserWrapper> getAllUser(@Param("email") String email);

    UserWrapper getUserById(@Param("userId") Integer userId);

}
