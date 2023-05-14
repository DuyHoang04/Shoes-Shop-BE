package com.ecomerce.Shoes_Shop.Wrapper;

import com.ecomerce.Shoes_Shop.POJO.ImageProduct;
import com.ecomerce.Shoes_Shop.POJO.Review;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ProductDetailWrapper {

    private Integer productId;

    private String name;

    private String description;

    private Double price;

    private Integer num_reviews;

    private Double rating;

    private String status;

    private List<ImageProduct> image;

    private Integer brandId;

    private String brand;

    private String tag;

    private List<ReviewWrapper> reviews;


    public ProductDetailWrapper(Integer productId, String name, String description, Double price, Integer num_reviews, Double rating, String status, List<ImageProduct> image, Integer brandId, String brand, String tag, List<ReviewWrapper> reviews) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.num_reviews = num_reviews;
        this.rating = rating;
        this.status = status;
        this.image = image;
        this.brandId = brandId;
        this.brand = brand;
        this.tag = tag;
        this.reviews = reviews;
    }
}
