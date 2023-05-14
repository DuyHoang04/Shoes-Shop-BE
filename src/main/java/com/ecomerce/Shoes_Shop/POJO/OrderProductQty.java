package com.ecomerce.Shoes_Shop.POJO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductQty {

    private Integer productId;

    private Integer quantity;
}
