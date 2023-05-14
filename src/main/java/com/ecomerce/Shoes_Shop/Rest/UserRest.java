package com.ecomerce.Shoes_Shop.Rest;

import com.ecomerce.Shoes_Shop.POJO.ResponseData;
import com.ecomerce.Shoes_Shop.POJO.ResponseMess;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.Map;

@RequestMapping(path = "/api/v1/users")
public interface UserRest {

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseMess> updateUser(@PathVariable(required = true) Integer id, @RequestBody Map<String, String> requestMap);


    @PostMapping("/change_password/{id}")
    public ResponseEntity<ResponseMess> changePassword(@PathVariable(name="id") Integer id, @RequestBody Map<String, String> requestMap);

    @GetMapping("")
    public ResponseEntity<ResponseData> getAllUser(@RequestParam(name = "email", defaultValue = "") String email);

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData> getUserDetail(@PathVariable(name = "id", required = true) Integer id);

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseMess> deleteUser(@PathVariable(name = "id", required = true) Integer id);

}
