package com.ecomerce.Shoes_Shop.POJO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

//@NamedQuery(name = "Cart.getCartWrappersByUser", query = "select new com.ecomerce.Shoes_Shop.Wrapper.CartWrapper(c.cartId, new com.ecomerce.Shoes_Shop.Wrapper.ProductWrapper(p.productId,p.name,p.description,p.price,p.status,p.productImages,p.brand.brandId,p.brand.name), c.quantity, new com.ecomerce.Shoes_Shop.Wrapper.UserWrapper(u.id,u.username,u.email,u.status,u.role)) from Cart c join c.product p join c.user u where u.userId = :userId")
@NamedQuery(name = "Cart.getCartWrappersByUser", query = "SELECT new com.ecomerce.Shoes_Shop.Wrapper.CartWrapper(c.cartId, p.name, p.productImages, p.price, c.quantity, u.email) FROM Cart c JOIN c.product p JOIN c.user u WHERE u.userId = :userId")

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "cart")
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartId;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
