package com.ecomerce.Shoes_Shop.RestImpl;

import com.ecomerce.Shoes_Shop.Contents.Constants;
import com.ecomerce.Shoes_Shop.POJO.OrderInput;
import com.ecomerce.Shoes_Shop.POJO.ResponseData;
import com.ecomerce.Shoes_Shop.POJO.ResponseMess;
import com.ecomerce.Shoes_Shop.Rest.OrderDetailRest;
import com.ecomerce.Shoes_Shop.Service.OrderDetailService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class OrderDetailRestImpl implements OrderDetailRest {

    private final OrderDetailService orderDetailService;

    @Override
    public ResponseEntity<ResponseMess> placeOrder( boolean is_cart_checkout, OrderInput orderInput) {
       try {
           return orderDetailService.placeOrder(is_cart_checkout, orderInput);
       } catch (Exception ex) {
           ex.printStackTrace();
       }
       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMess(false, Constants.SOMETHING_WENT_WRONG));
    }

    @Override
    public ResponseEntity<ResponseData> getAllOrder() {
       try {
           return orderDetailService.getAllOrder();
       } catch (Exception ex) {
           ex.printStackTrace();
       }
       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseData(false, Constants.SOMETHING_WENT_WRONG, ""));
    }
}
