package com.ecomerce.Shoes_Shop.Dao;

import com.ecomerce.Shoes_Shop.POJO.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewDao extends JpaRepository<Review, Integer> {
}
