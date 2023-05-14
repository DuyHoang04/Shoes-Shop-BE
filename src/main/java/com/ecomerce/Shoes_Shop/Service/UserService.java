package com.ecomerce.Shoes_Shop.Service;

import com.ecomerce.Shoes_Shop.POJO.ResponseData;
import com.ecomerce.Shoes_Shop.POJO.ResponseMess;
import org.springframework.http.ResponseEntity;

import java.awt.print.Pageable;
import java.util.Map;

public interface UserService {
    ResponseEntity<ResponseMess> updateUser(Integer id, Map<String, String> requestMap);

    ResponseEntity<ResponseMess> changePassword(Integer id, Map<String, String> requestMap);

    ResponseEntity<ResponseData> getAllUser(String email);

    ResponseEntity<ResponseData> getUserDetail(Integer id);

    ResponseEntity<ResponseMess> deleteUser(Integer id);
}
