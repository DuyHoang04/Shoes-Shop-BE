package com.ecomerce.Shoes_Shop.Dao;

import com.ecomerce.Shoes_Shop.POJO.Cart;
import com.ecomerce.Shoes_Shop.POJO.Product;
import com.ecomerce.Shoes_Shop.POJO.User;
import com.ecomerce.Shoes_Shop.Wrapper.CartWrapper;
import com.ecomerce.Shoes_Shop.Wrapper.ProductWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartDao extends JpaRepository<Cart, Integer> {

  public List<Cart> findByUser(User user);

  List<CartWrapper> getCartWrappersByUser(@Param("userId") Integer userId);

  Cart findByUserAndProduct(User user, Product product);
}
