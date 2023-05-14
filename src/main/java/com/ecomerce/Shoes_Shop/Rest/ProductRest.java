package com.ecomerce.Shoes_Shop.Rest;

import com.ecomerce.Shoes_Shop.POJO.ResponseData;
import com.ecomerce.Shoes_Shop.POJO.ResponseMess;
import com.ecomerce.Shoes_Shop.POJO.ReviewInput;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RequestMapping(path = "/api/v1/products")
public interface ProductRest {

    @PostMapping("")
    public ResponseEntity<ResponseData> addNewProduct(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "price", required = false) Double price,
            @RequestParam(value = "brandId", required = false) Integer brandId,
            @RequestParam(value = "file", required = false) MultipartFile[] file,
            @RequestParam(value = "tag", required = false) String tag

    );

    @GetMapping("")
    public ResponseEntity<ResponseData> getAllProducts(@RequestParam(name="page", defaultValue = "1", required = false) Integer page,
                                                       @RequestParam(name="size", defaultValue = "10", required = false) Integer size,
                                                       @RequestParam(name="tag", defaultValue = "", required = false) String tag,
                                                       @RequestParam(name="name", defaultValue = "", required = false) String name,
                                                       @RequestParam(name="brandId", defaultValue = "", required = false) Integer brandId,
                                                       @RequestParam(name="minPrice", defaultValue = "", required = false) Integer minPrice,
                                                       @RequestParam(name="maxPrice", defaultValue = "", required = false) Integer maxPrice);




    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseMess> deleteProduct(@PathVariable("id") Integer id);

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseMess> updateProduct(
            @PathVariable("id") Integer id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "price", required = false) Double price,
            @RequestParam(value = "brandId", required = false) Integer brandId,
            @RequestParam(value = "file", required = false) MultipartFile[] file,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "tag", required = false) String tag
    );


    @GetMapping("/{id}")
    public ResponseEntity<ResponseData> getProductDetail(@PathVariable("id") Integer id);

    @GetMapping("/admin")
    public ResponseEntity<ResponseData> getProductAdmin();

    @PostMapping("/{productId}/review")
    public ResponseEntity<ResponseMess> addReviewProduct(@PathVariable("productId") Integer productId, @RequestBody ReviewInput reviewInput);

}
