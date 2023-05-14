package com.ecomerce.Shoes_Shop.Rest;

import com.ecomerce.Shoes_Shop.POJO.AuthResponse;
import com.ecomerce.Shoes_Shop.POJO.LoginRequest;
import com.ecomerce.Shoes_Shop.POJO.ResponseMess;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping(path = "/api/v1/auth")
public interface AuthRest {

    @PostMapping("/register")
    public ResponseEntity<ResponseMess> register(@RequestBody(required = true) Map<String, String> requestMap);

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody(required = true) LoginRequest requestMap);


}
