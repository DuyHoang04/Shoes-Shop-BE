package com.ecomerce.Shoes_Shop.RestImpl;

import com.ecomerce.Shoes_Shop.Contents.Constants;
import com.ecomerce.Shoes_Shop.POJO.ResponseData;
import com.ecomerce.Shoes_Shop.POJO.ResponseMess;
import com.ecomerce.Shoes_Shop.Rest.CartRest;
import com.ecomerce.Shoes_Shop.Service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
public class CartRestImpl implements CartRest {

    private final CartService cartService;


    @Override
    public ResponseEntity<ResponseMess> addToCart(Integer productId, Map<String, Integer> requestMap, Boolean decrease) {
       try {
           return cartService.addToCart(productId, requestMap, decrease);
       } catch (Exception ex) {
           ex.printStackTrace();
       }
       return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMess(false, Constants.SOMETHING_WENT_WRONG));
    }

    @Override
    public ResponseEntity<ResponseData> getCartDetail() {
        try {
            return cartService.getCartDetail();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseData(false, Constants.SOMETHING_WENT_WRONG, ""));
    }
}
