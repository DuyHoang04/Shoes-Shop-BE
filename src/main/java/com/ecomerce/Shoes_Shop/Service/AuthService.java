package com.ecomerce.Shoes_Shop.Service;

import com.ecomerce.Shoes_Shop.POJO.AuthResponse;
import com.ecomerce.Shoes_Shop.POJO.LoginRequest;
import com.ecomerce.Shoes_Shop.POJO.ResponseMess;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface AuthService {
    ResponseEntity<ResponseMess> register(Map<String, String> requestMap);

    ResponseEntity<AuthResponse> login(LoginRequest requestMap);
}
