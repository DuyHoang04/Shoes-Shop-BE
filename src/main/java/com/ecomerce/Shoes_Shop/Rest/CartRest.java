package com.ecomerce.Shoes_Shop.Rest;

import com.ecomerce.Shoes_Shop.POJO.ResponseData;
import com.ecomerce.Shoes_Shop.POJO.ResponseMess;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping(path = "api/v1/carts")
public interface CartRest {

    @PostMapping("/add/{productId}")
    public ResponseEntity<ResponseMess> addToCart(
            @PathVariable("productId") Integer productId,
            @RequestBody Map<String, Integer> requestMap,
            @RequestParam(value = "decrease", required = false) Boolean decrease
    );

    @GetMapping("/getCartDetail")
    public ResponseEntity<ResponseData> getCartDetail();
}
