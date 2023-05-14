package com.ecomerce.Shoes_Shop.ServiceImpl;

import com.ecomerce.Shoes_Shop.Contents.Constants;
import com.ecomerce.Shoes_Shop.Dao.BrandDao;
import com.ecomerce.Shoes_Shop.JWT.JwtFilter;
import com.ecomerce.Shoes_Shop.POJO.Brand;
import com.ecomerce.Shoes_Shop.POJO.ResponseData;
import com.ecomerce.Shoes_Shop.POJO.ResponseMess;
import com.ecomerce.Shoes_Shop.Service.BrandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final JwtFilter jwtFilter;

    private final BrandDao brandDao;

    @Override
    public ResponseEntity<ResponseMess> createBrand(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()) {
                brandDao.save(Brand
                        .builder()
                        .name(requestMap.get("name"))
                        .build());
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMess(false, "SUCCESSFULLY"));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMess(false, Constants.UNAUTHORIZED));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMess(false, Constants.SOMETHING_WENT_WRONG));
    }

    @Override
    public ResponseEntity<ResponseData> getAllBrand() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseData(true, "SUCCESSFULLY", brandDao.findAll()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseData(false, Constants.SOMETHING_WENT_WRONG, ""));
    }
}
