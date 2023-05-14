package com.ecomerce.Shoes_Shop.Wrapper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewWrapper {

    private Integer userId;

    private String username;

    private String rating;

    private String comment;

}
