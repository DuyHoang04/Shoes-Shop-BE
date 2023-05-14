package com.ecomerce.Shoes_Shop.Wrapper;

import com.ecomerce.Shoes_Shop.POJO.ImageProduct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CartWrapper {

    private Integer cartId;

    private Integer productId;
    private String nameProduct;

    private List<ImageProduct> image;
    private Double price;
    private Integer quantity;

    private String emailUser;

    public CartWrapper(Integer cartId, Integer productId, String nameProduct, List<ImageProduct> image, Double price, Integer quantity, String emailUser) {
        this.cartId = cartId;
        this.productId = productId;
        this.nameProduct = nameProduct;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
        this.emailUser = emailUser;
    }
}
