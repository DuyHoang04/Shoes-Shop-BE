package com.ecomerce.Shoes_Shop.Dao;

import com.ecomerce.Shoes_Shop.POJO.ImageProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageDao extends  JpaRepository<ImageProduct, Long> {
    Optional<ImageProduct> findByName(String name);
}
