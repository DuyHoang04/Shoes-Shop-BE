package com.ecomerce.Shoes_Shop.Service;

import com.ecomerce.Shoes_Shop.POJO.OrderInput;
import com.ecomerce.Shoes_Shop.POJO.ResponseData;
import com.ecomerce.Shoes_Shop.POJO.ResponseMess;
import org.springframework.http.ResponseEntity;

public interface OrderDetailService {
    ResponseEntity<ResponseMess> placeOrder( boolean is_cart_checkout, OrderInput orderInput);

    ResponseEntity<ResponseData> getAllOrder();
}
