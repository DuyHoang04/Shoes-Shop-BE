package com.ecomerce.Shoes_Shop.ServiceImpl;

import com.ecomerce.Shoes_Shop.Contents.Constants;
import com.ecomerce.Shoes_Shop.Dao.CartDao;
import com.ecomerce.Shoes_Shop.Dao.OrderDetailDao;
import com.ecomerce.Shoes_Shop.Dao.ProductDao;
import com.ecomerce.Shoes_Shop.Dao.UserDao;
import com.ecomerce.Shoes_Shop.JWT.JwtFilter;
import com.ecomerce.Shoes_Shop.POJO.*;
import com.ecomerce.Shoes_Shop.Service.OrderDetailService;
import com.ecomerce.Shoes_Shop.Wrapper.OrderWrapper;
import com.ecomerce.Shoes_Shop.Wrapper.ProductWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {

    private static final String ORDER_PLACE = "Placed";

    private final OrderDetailDao orderDetailDao;

    private final ProductDao productDao;

    private final CartDao cartDao;

    private final UserDao userDao;

    private final JwtFilter jwtFilter;

    @Override
    public ResponseEntity<ResponseMess> placeOrder(boolean is_cart_checkout, OrderInput orderInput) {
        try {
            List<OrderProductQty> productQuantityList = orderInput.getOrderProductQuantityList();

            String currentUser = jwtFilter.getCurrentUser();
            User user = userDao.findByEmail(currentUser).get();

            for(OrderProductQty item: productQuantityList) {
                Product product = productDao.findById(item.getProductId()).get();
                orderDetailDao.save(OrderDetail
                       .builder()
                       .orderFullName(orderInput.getFullName())
                       .orderAddress(orderInput.getFullAddress())
                       .orderContactNumber(orderInput.getContactNumber())
                       .orderStatus(ORDER_PLACE)
                       .orderAmount(product.getPrice() * item.getQuantity())
                       .product(product)
                       .user(user)
                       .build()
                );
            }

            if(is_cart_checkout) {
                List<Cart> listCarts = cartDao.findByUser(user);
                listCarts.stream().forEach(item -> cartDao.delete(item));
            }
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMess(true, "ORDER SUCCESSFULLY"));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMess(false, Constants.SOMETHING_WENT_WRONG));
    }

    @Override
    public ResponseEntity<ResponseData> getAllOrder() {
        try {
           List<OrderWrapper> ordersList = new ArrayList<>();
            for (OrderDetail order : orderDetailDao.findAll()) {
                var productData = order.getProduct();
                List<ImageProduct> images = new ArrayList<>(productData.getProductImages());
                ProductWrapper product = new ProductWrapper(
                        productData.getProductId(),
                        productData.getName(),
                        productData.getDescription(),
                        productData.getPrice(),
                        productData.getRating(),
                        productData.getStatus(),
                        images,
                        productData.getBrand().getBrandId(),
                        productData.getBrand().getName(),
                        productData.getTag()
                );

                OrderWrapper orderWrapper = new OrderWrapper(
                        order.getOrderId(),
                        order.getOrderAddress(),
                        order.getOrderFullName(),
                        order.getOrderContactNumber(),
                        order.getOrderStatus(),
                        order.getOrderAmount(),
                        order.getUser().getUserId(),
                        order.getUser().getEmail(),
                        product
                );
                ordersList.add(orderWrapper);
            }
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseData(true, "Success", ordersList));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseData(false, Constants.SOMETHING_WENT_WRONG, ""));
    }
}
