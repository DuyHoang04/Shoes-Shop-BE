package com.ecomerce.Shoes_Shop.POJO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderInput {
    private String fullName;

    private String fullAddress;

    private String contactNumber;

    private List<OrderProductQty> orderProductQuantityList;
}
