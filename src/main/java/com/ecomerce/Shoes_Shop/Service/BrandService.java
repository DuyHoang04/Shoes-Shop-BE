package com.ecomerce.Shoes_Shop.Service;

import com.ecomerce.Shoes_Shop.POJO.ResponseData;
import com.ecomerce.Shoes_Shop.POJO.ResponseMess;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface BrandService {
    ResponseEntity<ResponseMess> createBrand(Map<String, String> requestMap);

    ResponseEntity<ResponseData> getAllBrand();
}
