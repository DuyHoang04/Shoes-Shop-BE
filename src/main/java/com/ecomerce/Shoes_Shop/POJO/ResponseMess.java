package com.ecomerce.Shoes_Shop.POJO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMess {
    private Boolean success;

    private String message;
}
