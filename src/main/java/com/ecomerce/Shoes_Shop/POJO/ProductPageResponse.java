package com.ecomerce.Shoes_Shop.POJO;

import com.ecomerce.Shoes_Shop.Wrapper.ProductWrapper;
import com.ecomerce.Shoes_Shop.Wrapper.UserWrapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ProductPageResponse {
    private Integer activePage;
    private Integer totalPage;
    private Integer totalProduct;
    private List<ProductWrapper> products;
}
