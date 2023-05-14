package com.ecomerce.Shoes_Shop.Dao;

import com.ecomerce.Shoes_Shop.POJO.Product;
import com.ecomerce.Shoes_Shop.Wrapper.ProductWrapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

@EnableJpaRepositories
public interface ProductDao extends JpaRepository<Product, Integer> {

    Page<Product> findByNameAndTagContainingIgnoreCase(String name, String tag, Pageable pageable);

    List<ProductWrapper> getProductAdmin();

    Product findByProductId(Integer productId);

}
