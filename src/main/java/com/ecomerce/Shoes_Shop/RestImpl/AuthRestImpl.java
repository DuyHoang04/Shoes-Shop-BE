package com.ecomerce.Shoes_Shop.RestImpl;

import com.ecomerce.Shoes_Shop.Contents.Constants;
import com.ecomerce.Shoes_Shop.POJO.AuthResponse;
import com.ecomerce.Shoes_Shop.POJO.LoginRequest;
import com.ecomerce.Shoes_Shop.POJO.ResponseMess;
import com.ecomerce.Shoes_Shop.Rest.AuthRest;
import com.ecomerce.Shoes_Shop.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AuthRestImpl implements AuthRest {

    @Autowired
    AuthService userService;

    @Override
    public ResponseEntity<ResponseMess> register(Map<String, String> requestMap) {
        try {
            return userService.register(requestMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMess(false, Constants.SOMETHING_WENT_WRONG));
    }

    @Override
    public ResponseEntity<AuthResponse> login(LoginRequest requestMap) {
        try {
            return userService.login(requestMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AuthResponse(false, Constants.SOMETHING_WENT_WRONG, ""));
    }
}
