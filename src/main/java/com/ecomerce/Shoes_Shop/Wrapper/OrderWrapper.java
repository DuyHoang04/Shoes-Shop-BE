package com.ecomerce.Shoes_Shop.Wrapper;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class OrderWrapper {

    private Integer orderId;

    private String address;

    private String fullName;

    private String contactNumber;

    private String status;

    private Double orderAmount;

    private Integer userId;

    private String email;


    private ProductWrapper product;


    public OrderWrapper(Integer orderId, String address, String fullName, String contactNumber, String status, Double orderAmount, Integer userId, String email, ProductWrapper product) {
        this.orderId = orderId;
        this.address = address;
        this.fullName = fullName;
        this.contactNumber = contactNumber;
        this.status = status;
        this.orderAmount = orderAmount;
        this.userId = userId;
        this.email = email;
        this.product = product;
    }

}
