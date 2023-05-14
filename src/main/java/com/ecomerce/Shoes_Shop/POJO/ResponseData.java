package com.ecomerce.Shoes_Shop.POJO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseData {

    private Boolean success;

    private String message;

    private Object data;

}
