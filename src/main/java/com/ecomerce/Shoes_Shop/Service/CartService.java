package com.ecomerce.Shoes_Shop.Service;

import com.ecomerce.Shoes_Shop.POJO.ResponseData;
import com.ecomerce.Shoes_Shop.POJO.ResponseMess;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface CartService {
    ResponseEntity<ResponseMess> addToCart(Integer productId, Map<String, Integer> requestMap, Boolean decrease);

    ResponseEntity<ResponseData> getCartDetail();
}
