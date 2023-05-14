package com.ecomerce.Shoes_Shop.ServiceImpl;

import com.ecomerce.Shoes_Shop.Contents.Constants;
import com.ecomerce.Shoes_Shop.Dao.CartDao;
import com.ecomerce.Shoes_Shop.Dao.ProductDao;
import com.ecomerce.Shoes_Shop.Dao.UserDao;
import com.ecomerce.Shoes_Shop.JWT.JwtFilter;
import com.ecomerce.Shoes_Shop.POJO.*;
import com.ecomerce.Shoes_Shop.Service.CartService;
import com.ecomerce.Shoes_Shop.Wrapper.CartWrapper;
import com.ecomerce.Shoes_Shop.Wrapper.ProductWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final ProductDao productDao;
    private final CartDao cartDao;

    private final UserDao userDao;

    private final JwtFilter jwtFilter;

    @Override
    public ResponseEntity<ResponseMess> addToCart(Integer productId, Map<String, Integer> requestMap,  Boolean decrease) {
        try {
            Product product = productDao.findById(productId).orElse(null);
            User user = userDao.findByEmail(jwtFilter.getCurrentUser()).orElse(null);
            Cart cart = cartDao.findByUserAndProduct(user, product);

            if(user == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMess(true, "USER NOT FOUND"));
            }

            if(product == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMess(false, "PRODUCT NOT FOUND"));
            }

            if (decrease) {
                decreaseCartItemQuantity(user, product);
            } else {
                addCartItem(user, product, requestMap.get("quantity"));
            }

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMess(true, "SUCCESSFULLY"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMess(false, Constants.SOMETHING_WENT_WRONG));
    }

    private void addCartItem(User user, Product product, int quantity) {
        Cart cart = cartDao.findByUserAndProduct(user, product);
        if (cart != null) {
            int newQuantity = cart.getQuantity() + quantity;
            cart.setQuantity(newQuantity);
            cartDao.save(cart);
        } else {
            cartDao.save(Cart.builder()
                    .product(product)
                    .user(user)
                    .quantity(quantity)
                    .build());
        }
    }

    private void decreaseCartItemQuantity(User user, Product product) {
        Cart cart = cartDao.findByUserAndProduct(user, product);
        if (cart != null) {
            int newQuantity = cart.getQuantity() - 1;
            if (newQuantity > 0) {
                cart.setQuantity(newQuantity);
                cartDao.save(cart);
            } else {
                cartDao.delete(cart);
            }
        }
    }

    @Override
    public ResponseEntity<ResponseData> getCartDetail() {
        try {
             List<CartWrapper> cartWrappers = new ArrayList<>();
             User user = userDao.findByEmail(jwtFilter.getCurrentUser()).get();
            List<Cart> carts = cartDao.findByUser(user);

            for(Cart cart: carts) {
                CartWrapper cartWrapper = new CartWrapper(
                        cart.getCartId(),
                        cart.getProduct().getProductId(),
                        cart.getProduct().getName(),
                        new ArrayList<>(cart.getProduct().getProductImages()),
                        cart.getProduct().getPrice(),
                        cart.getQuantity(),
                        user.getEmail()
                );
                cartWrappers.add(cartWrapper);
            }

            return  ResponseEntity.status(HttpStatus.OK).body(new ResponseData(true, "GET CART SUCCESSFULY", cartWrappers));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseData(false, Constants.SOMETHING_WENT_WRONG, ""));
    }
}
