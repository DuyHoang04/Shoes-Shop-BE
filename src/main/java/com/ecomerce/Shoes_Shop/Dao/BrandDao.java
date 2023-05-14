package com.ecomerce.Shoes_Shop.Dao;

import com.ecomerce.Shoes_Shop.POJO.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrandDao extends JpaRepository<Brand, Integer> {

    List<Brand> getAllBrand();
}
