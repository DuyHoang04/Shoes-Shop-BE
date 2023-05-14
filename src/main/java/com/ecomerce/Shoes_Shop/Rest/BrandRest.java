package com.ecomerce.Shoes_Shop.Rest;

import com.ecomerce.Shoes_Shop.POJO.ResponseData;
import com.ecomerce.Shoes_Shop.POJO.ResponseMess;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping(path = "/api/v1/brands")
public interface BrandRest {

        @PostMapping("")
        public ResponseEntity<ResponseMess> createBrand(@RequestBody Map<String, String> requestMap);

        @GetMapping("")
        public ResponseEntity<ResponseData> getAllBrand();


}
