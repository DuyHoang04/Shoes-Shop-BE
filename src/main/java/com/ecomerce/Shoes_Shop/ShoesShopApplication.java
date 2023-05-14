package com.ecomerce.Shoes_Shop;

import com.ecomerce.Shoes_Shop.Dao.ImageDao;
import com.ecomerce.Shoes_Shop.POJO.ImageProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;


@RestController
@SpringBootApplication
public class ShoesShopApplication {

	@Autowired
	ImageDao imageDao;

	@GetMapping("/api/v1/images/{fileName}")
	public ResponseEntity<?> downloadImageSystem(@PathVariable String fileName) throws IOException {
		Optional<ImageProduct> fileData = imageDao.findByName(fileName);
		String filePath = fileData.get().getFilePath();
		byte[] images = Files.readAllBytes(new File(filePath).toPath());
		return ResponseEntity.status(HttpStatus.OK)
				.contentType(MediaType.valueOf("image/png"))
				.body(images);
	}

	public static void main(String[] args) {
		SpringApplication.run(ShoesShopApplication.class, args);
	}

}
