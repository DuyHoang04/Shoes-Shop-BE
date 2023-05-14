package com.ecomerce.Shoes_Shop.Service;

import com.ecomerce.Shoes_Shop.POJO.ResponseData;
import com.ecomerce.Shoes_Shop.POJO.ResponseMess;
import com.ecomerce.Shoes_Shop.POJO.ReviewInput;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface ProductService {
    ResponseEntity<ResponseData> addNewProduct(String name, String description, Double price, Integer brandId, MultipartFile[] file, String tag);

    ResponseEntity<ResponseData> getAllProducts(Integer page, Integer size, String tag, String name, Integer brandId, Integer minPrice, Integer maxPrice);

    ResponseEntity<ResponseMess> deleteProduct(Integer id);

    ResponseEntity<ResponseMess> updateProduct(Integer id, String name, String description, Double price, Integer brandId, MultipartFile[] file, String status, String tag);

    ResponseEntity<ResponseData> getProductDetail(Integer id);

    ResponseEntity<ResponseData> getProductAdmin();

    ResponseEntity<ResponseMess> addCommentProduct(Integer productId, ReviewInput reviewInput);
}
