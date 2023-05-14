package com.ecomerce.Shoes_Shop.Wrapper;

import com.ecomerce.Shoes_Shop.POJO.ImageProduct;
import com.ecomerce.Shoes_Shop.POJO.Product;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;


@Data
@NoArgsConstructor
public class ProductWrapper {

    private Integer productId;

    private String name;

    private String description;

    private Double price;

    private Double rating;

    private String status;

    private List<ImageProduct> image;

    private Integer brandId;

    private String brand;

    private String tag;


    public ProductWrapper(Integer productId, String name, String description, Double price, Double rating, String status, List<ImageProduct> image, Integer brandId, String brand, String tag) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.rating = rating;
        this.status = status;
        this.image = image;
        this.brandId = brandId;
        this.brand = brand;
        this.tag = tag;
    }


}
