package com.ecomerce.Shoes_Shop.Wrapper;

import com.ecomerce.Shoes_Shop.POJO.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserWrapper {
    private Integer userId;

    private String username;

    private String email;

    private String status;

    private Role role;
}
