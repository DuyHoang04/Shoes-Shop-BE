package com.ecomerce.Shoes_Shop.RestImpl;

import com.ecomerce.Shoes_Shop.Contents.Constants;
import com.ecomerce.Shoes_Shop.POJO.ResponseData;
import com.ecomerce.Shoes_Shop.POJO.ResponseMess;
import com.ecomerce.Shoes_Shop.POJO.ReviewInput;
import com.ecomerce.Shoes_Shop.Rest.ProductRest;
import com.ecomerce.Shoes_Shop.Service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@AllArgsConstructor
public class ProductRestImpl implements ProductRest {

    private final ProductService productService;
    @Override
    public ResponseEntity<ResponseData> addNewProduct(String name, String description, Double price, Integer brandId, MultipartFile[] file, String tag) {
       try {
           return productService.addNewProduct(name, description, price, brandId, file, tag);
       } catch (Exception ex) {
           ex.printStackTrace();
       }
       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseData(false, Constants.SOMETHING_WENT_WRONG, ""));
    }

    @Override
    public ResponseEntity<ResponseData> getAllProducts(Integer page, Integer size, String tag, String name, Integer brandId, Integer minPrice, Integer maxPrice ) {
        try {
            return productService.getAllProducts(page, size, tag, name, brandId, minPrice, maxPrice);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseData(false, Constants.SOMETHING_WENT_WRONG, ""));
    }

    @Override
    public ResponseEntity<ResponseMess> deleteProduct(Integer id) {
        try {
            return productService.deleteProduct(id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMess(false, Constants.SOMETHING_WENT_WRONG));
    }



    @Override
    public ResponseEntity<ResponseMess> updateProduct(Integer id, String name, String description, Double price, Integer brandId, MultipartFile[] file, String status, String tag) {
        try {
            return productService.updateProduct(id, name, description, price, brandId, file, status, tag);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMess(false, Constants.SOMETHING_WENT_WRONG));
    }

    @Override
    public ResponseEntity<ResponseData> getProductDetail(Integer id) {
        try {
            return productService.getProductDetail(id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseData(false, Constants.SOMETHING_WENT_WRONG, ""));
    }

    @Override
    public ResponseEntity<ResponseData> getProductAdmin() {
       try {
           return productService.getProductAdmin();
       } catch (Exception ex) {
           ex.printStackTrace();
       }
       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseData(false, Constants.SOMETHING_WENT_WRONG, ""));
    }

    @Override
    public ResponseEntity<ResponseMess> addReviewProduct(Integer productId, ReviewInput reviewInput) {
        try {
            return productService.addCommentProduct(productId, reviewInput);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMess(false, Constants.SOMETHING_WENT_WRONG));
    }

}
