package com.ecomerce.Shoes_Shop.ServiceImpl;

import com.ecomerce.Shoes_Shop.Contents.Constants;
import com.ecomerce.Shoes_Shop.Dao.UserDao;
import com.ecomerce.Shoes_Shop.JWT.JwtFilter;
import com.ecomerce.Shoes_Shop.JWT.JwtUntil;
import com.ecomerce.Shoes_Shop.POJO.*;
import com.ecomerce.Shoes_Shop.Service.UserService;
import com.ecomerce.Shoes_Shop.Wrapper.UserWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserDao userDao;

    private final JwtFilter jwtFilter;

    private final JwtUntil jwtUntil;

    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<ResponseMess> updateUser(Integer id, Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()) {
                Optional<User> userCheck = userDao.findById(id);
                if(userCheck.isPresent()) {
                    isUpdateUser(userCheck, requestMap);
                    return ResponseEntity.status(HttpStatus.OK).body(new ResponseMess(true, "Update User Successfully"));
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMess(false, "User is not valid"));
                }
            }
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMess(false, Constants.UNAUTHORIZED));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMess(false, Constants.SOMETHING_WENT_WRONG));
    }

    private void isUpdateUser(Optional<User> userCheck, Map<String, String> requestMap) {
        User user = userCheck.get();
        String email = requestMap.get("email");
        if(email != null) {
            user.setEmail(email);
        }
        String username = requestMap.get("username");
        if(username != null) {
            user.setUsername(username);
        }
        String status = requestMap.get("status");
        if(status != null) {
            user.setStatus(status);
        }
        String role = requestMap.get("role");
        if(role != null) {
            user.setRole(Role.valueOf(role));
        }
        userDao.save(user);
    }

    @Override
    public ResponseEntity<ResponseMess> changePassword(Integer id, Map<String, String> requestMap) {
        try {
            User userCheck = userDao.findById(id).get();
            if(userCheck != null) {
                if(passwordEncoder.matches(requestMap.get("oldPassword"), userCheck.getPassword())) {
                    userCheck.setPassword(passwordEncoder.encode(requestMap.get("newPassword")));
                    var newUser = userDao.save(userCheck);

                    return ResponseEntity.status(HttpStatus.OK).body(new ResponseMess(false, "Change Password Success"));
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMess(false, "INCORRECT PASSWORD"));
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMess(false, "YOU ARE NOT USER"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMess(false, Constants.SOMETHING_WENT_WRONG));
    }

    @Override
    public ResponseEntity<ResponseData> getAllUser(String email) {
        try {
//            int activePage = page - 1;
//
//            Pageable p = PageRequest.of(activePage, size);
//
//            Page<User> userPage = userDao.findByEmailContainingIgnoreCase(email, p);
//
//            int totalPage = userPage.getTotalPages();
//
//            List<UserWrapper> userList = userDao.getAllUser(email, p);




//            UserPageResponse usersData = new UserPageResponse(page, totalPage, userList);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseData(true, "GET ALL USER", userDao.getAllUser(email)));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseData(false, Constants.SOMETHING_WENT_WRONG, ""));
    }

    @Override
    public ResponseEntity<ResponseData> getUserDetail(Integer id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseData(false, "SUCCESS", userDao.getUserById(id)));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseData(false, Constants.SOMETHING_WENT_WRONG, ""));
    }

    @Override
    public ResponseEntity<ResponseMess> deleteUser(Integer id) {
        try {
            if(jwtFilter.isAdmin()) {
                userDao.deleteById(id);
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMess(true, "DELETE USER SUCCESSFULLY"));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMess(false, Constants.UNAUTHORIZED ));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMess(false, Constants.SOMETHING_WENT_WRONG));
    }


}
