package com.ecomerce.Shoes_Shop.RestImpl;

import com.ecomerce.Shoes_Shop.Contents.Constants;
import com.ecomerce.Shoes_Shop.POJO.ResponseData;
import com.ecomerce.Shoes_Shop.POJO.ResponseMess;
import com.ecomerce.Shoes_Shop.Rest.BrandRest;
import com.ecomerce.Shoes_Shop.Service.BrandService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
public class BrandRestImpl implements BrandRest {

    private final BrandService brandService;

    @Override
    public ResponseEntity<ResponseMess> createBrand(Map<String, String> requestMap) {
       try {
           return brandService.createBrand(requestMap);
       } catch (Exception ex) {
           ex.printStackTrace();
       }
       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMess(false, Constants.SOMETHING_WENT_WRONG));
    }

    @Override
    public ResponseEntity<ResponseData> getAllBrand() {
       try {
           return brandService.getAllBrand();
       } catch (Exception ex) {
           ex.printStackTrace();
       }
       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseData(false, Constants.SOMETHING_WENT_WRONG, ""));
    }
}
