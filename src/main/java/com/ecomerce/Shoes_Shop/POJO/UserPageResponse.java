package com.ecomerce.Shoes_Shop.POJO;

import com.ecomerce.Shoes_Shop.Wrapper.UserWrapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Wrapper;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPageResponse {
    private int activePage;
    private int totalPage;
    private List<UserWrapper> users;

}
