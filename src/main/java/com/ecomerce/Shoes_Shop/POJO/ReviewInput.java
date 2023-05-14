package com.ecomerce.Shoes_Shop.POJO;

import lombok.Data;

@Data
public class ReviewInput {
    private String username;

    private String comment;

    private Integer rating;
}
