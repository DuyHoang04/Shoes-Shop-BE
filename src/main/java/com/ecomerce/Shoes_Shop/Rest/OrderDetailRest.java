package com.ecomerce.Shoes_Shop.Rest;

import com.ecomerce.Shoes_Shop.POJO.OrderInput;
import com.ecomerce.Shoes_Shop.POJO.ResponseData;
import com.ecomerce.Shoes_Shop.POJO.ResponseMess;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "api/v1/orders")
public interface OrderDetailRest {

    @PostMapping("")
    public ResponseEntity<ResponseMess> placeOrder(
            @RequestParam(name = "is_cart_checkout") boolean is_cart_checkout,
            @RequestBody OrderInput orderInput
    );

    @GetMapping("")
    public ResponseEntity<ResponseData> getAllOrder();

}
