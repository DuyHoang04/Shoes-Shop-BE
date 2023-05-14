package com.ecomerce.Shoes_Shop.ServiceImpl;

import com.ecomerce.Shoes_Shop.Contents.Constants;
import com.ecomerce.Shoes_Shop.Dao.ProductDao;
import com.ecomerce.Shoes_Shop.Dao.ReviewDao;
import com.ecomerce.Shoes_Shop.Dao.UserDao;
import com.ecomerce.Shoes_Shop.JWT.JwtFilter;
import com.ecomerce.Shoes_Shop.POJO.*;
import com.ecomerce.Shoes_Shop.Service.ProductService;
import com.ecomerce.Shoes_Shop.Wrapper.ProductDetailWrapper;
import com.ecomerce.Shoes_Shop.Wrapper.ProductWrapper;
import com.ecomerce.Shoes_Shop.Wrapper.ReviewWrapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;

    private final JwtFilter jwtFilter;


    private final ServletContext servletContext;

    private final EntityManager entityManager;

    private final UserDao userDao;

    private final ReviewDao reviewDao;

    @Override
    public ResponseEntity<ResponseData> addNewProduct(String name, String description, Double price, Integer brandId, MultipartFile[] file, String tag) {

       try {
          if(jwtFilter.isAdmin()) {
              Set<ImageProduct> images = uploadImage(file);
              productDao.save(Product
                      .builder()
                              .name(name)
                              .description(description)
                              .price(price)
                              .brand(Brand.builder().brandId(brandId).build())
                              .productImages(images)
                              .numReviews(0)
                              .rating(0.0)
                              .status("Còn Hàng")
                              .tag(tag)
                      .build());

              return ResponseEntity.status(HttpStatus.OK).body(new ResponseData(true, "UPLOAD SUCCESS", ""));
          } else {
              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseData(false, Constants.UNAUTHORIZED, ""));
          }

       } catch (Exception ex) {
           ex.printStackTrace();
       }
       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseData(false, Constants.SOMETHING_WENT_WRONG, ""));
    }

    @Override
    public ResponseEntity<ResponseData> getAllProducts(Integer page, Integer size, String tag, String name, Integer brandId, Integer minPrice, Integer maxPrice) {
        try {
            System.out.println(minPrice);
            System.out.println(maxPrice);

            int activePage = page - 1;

            // BUILDER QUERY PRODUCT
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Product> queryProduct = criteriaBuilder.createQuery(Product.class);
            Root<Product> rootProduct = queryProduct.from(Product.class);
            Join<Product, Brand> brandJoin = rootProduct.join("brand", JoinType.LEFT);
            List<Predicate> predicates = new ArrayList<>();

            // COUNT PRODUCT
            CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
            Root<Product> countRoot = countQuery.from(Product.class);
            Join<Product, Brand> countBrandJoin = countRoot.join("brand", JoinType.LEFT);

            List<Predicate> countPredicates = new ArrayList<>();


            // FILTER
            if(name != null && !name.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(rootProduct.get("name")), "%" + name.toLowerCase() + "%"));
                countPredicates.add(criteriaBuilder.like(criteriaBuilder.lower(countRoot.get("name")), "%" + name.toLowerCase() + "%"));
            }
            if (tag != null && !tag.isEmpty()) {
                predicates.add(criteriaBuilder.equal(rootProduct.get("tag"), tag));
                countPredicates.add(criteriaBuilder.equal(countRoot.get("tag"), tag));
            }
            if (brandId != null) {
                predicates.add(criteriaBuilder.equal(brandJoin.get("brandId"), Long.parseLong(String.valueOf(brandId))));
                countPredicates.add(criteriaBuilder.equal(brandJoin.get("brandId"), Long.parseLong(String.valueOf(brandId))));
            }
            if (minPrice != null && maxPrice != null) {
                predicates.add(criteriaBuilder.between(rootProduct.get("price"), minPrice, maxPrice));
                countPredicates.add(criteriaBuilder.between(countRoot.get("price"), minPrice, maxPrice));
            }


            // PRODUCT
             queryProduct.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
            TypedQuery<Product> typedQuery = entityManager.createQuery(queryProduct);
            typedQuery.setFirstResult(activePage * size);
            typedQuery.setMaxResults(size);

            // COUNT PRODUCT
            countQuery.select(criteriaBuilder.count(countRoot));
            countQuery.where(criteriaBuilder.and(countPredicates.toArray(new Predicate[countPredicates.size()])));

            Long totalProduct = entityManager.createQuery(countQuery).getSingleResult();

            int totalPage = (int) Math.ceil(totalProduct / (double) size);

            // LIST PRODUCT
            List<Product> products = typedQuery.getResultList();
            List<ProductWrapper> productsList = products.stream().map(product -> {
                List<ImageProduct> images = new ArrayList<>(product.getProductImages());
                return new ProductWrapper(product.getProductId(), product.getName(), product.getDescription(), product.getPrice(), product.getRating(),
                        product.getStatus(), images, product.getBrand().getBrandId(), product.getBrand().getName(), product.getTag());
            }).collect(Collectors.toList());


            ProductPageResponse productData = new ProductPageResponse(page, totalPage, Math.toIntExact(totalProduct), productsList);

         return ResponseEntity.status(HttpStatus.OK).body(new ResponseData(true, "GET ALL PRODUCT SUCCESSFULLY", productData));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseData(false, Constants.SOMETHING_WENT_WRONG, ""));
    }

    @Override
    public ResponseEntity<ResponseMess> deleteProduct(Integer id) {
        try {
            if(jwtFilter.isAdmin()) {
                productDao.deleteById(id);
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMess(true, "Delete Product Successfully"));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMess(false, Constants.UNAUTHORIZED));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMess(false, Constants.SOMETHING_WENT_WRONG));
    }

    @Override
    public ResponseEntity<ResponseMess> updateProduct(Integer id, String name, String description, Double price, Integer brandId, MultipartFile[] file, String status, String tag) {
        try {
              Optional<Product> productCheck = productDao.findById(id);
              if(jwtFilter.isAdmin()) {
                  Set<ImageProduct> newImage = uploadImage(file);


                  Optional<Product> update = productCheck.map(product -> {
                      product.setName(name);
                      product.setDescription(description);
                      product.setPrice(price);
                      product.setBrand(Brand.builder().brandId(brandId).build());
                      product.setProductImages(file != null ? newImage : product.getProductImages());
                      product.setStatus(status);
                      product.setTag(tag);
                      return productDao.save(product);
                  });
                  return ResponseEntity.status(HttpStatus.OK).body(new ResponseMess(true, "UPDATE PRODUCT SUCCESSFULLY"));
              } else {
                  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMess(false, Constants.UNAUTHORIZED));
              }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMess(false, Constants.SOMETHING_WENT_WRONG));
    }

    @Override
    public ResponseEntity<ResponseData> getProductDetail(Integer id) {
        try {
              Optional<Product> productCheck = productDao.findById(id);
             Optional<ProductDetailWrapper> productDetail = productCheck.map(product -> {
                 List<ImageProduct> images = new ArrayList<>(product.getProductImages());
                 List<ReviewWrapper> reviews = new ArrayList<>();
                 for (Review review : product.getReviews()) {
                     ReviewWrapper reviewWrapper = new ReviewWrapper();
                     reviewWrapper.setUserId(review.getUser().getUserId());
                     reviewWrapper.setUsername(review.getUsername());
                     reviewWrapper.setRating(String.valueOf(review.getRating()));
                     reviewWrapper.setComment(review.getComment());
                     reviews.add(reviewWrapper);
                 }

                 return new ProductDetailWrapper(product.getProductId(), product.getName(), product.getDescription(), product.getPrice(), product.getNumReviews(), product.getRating(),
                        product.getStatus(), images, product.getBrand().getBrandId(), product.getBrand().getName(),product.getTag(), reviews);
            });

             return ResponseEntity.status(HttpStatus.OK).body(new ResponseData(true, "GET PRODUCT SUCCESSFULLY", productDetail));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseData(false, Constants.SOMETHING_WENT_WRONG, ""));
    }

    @Override
    public ResponseEntity<ResponseData> getProductAdmin() {
        try {
            List<Product> productCheck = productDao.findAll();
            List<ProductWrapper> productList = productCheck.stream().map(product -> {
                List<ImageProduct> images = new ArrayList<>(product.getProductImages());
                return new ProductWrapper(product.getProductId(), product.getName(), product.getDescription(), product.getPrice(), product.getRating(),
                        product.getStatus(), images, product.getBrand().getBrandId(), product.getBrand().getName(), product.getTag());
            }).collect(Collectors.toList());

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseData(true, "GET SUCCESS", productList));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseData(false, Constants.SOMETHING_WENT_WRONG, ""));
    }

    @Override
    public ResponseEntity<ResponseMess> addCommentProduct(Integer productId, ReviewInput reviewInput) {
        try {
            User user = userDao.findUserByEmail(jwtFilter.getCurrentUser());
            Product product = productDao.findById(productId).get();

           if(user != null && product != null) {

               Review reviewProduct = Review.builder()
                       .comment(reviewInput.getComment())
                       .rating(reviewInput.getRating())
                       .user(user)
                       .username(reviewInput.getUsername())
                       .build();

               if(product.getReviews().isEmpty()) {
                   Set<Review> reviews = new HashSet<>();
                   reviews.add(reviewProduct);
                   product.setReviews(reviews);
               } else {
                   product.getReviews().add(reviewProduct);
               }

               product.setNumReviews(product.getNumReviews() + 1);

               Double sumRating = product.getRating() * (product.getNumReviews() - 1.0) + reviewInput.getRating();
               product.setRating(sumRating / product.getNumReviews());

//              reviewDao.save(reviewProduct);
                productDao.save(product);

               return ResponseEntity.status(HttpStatus.OK).body(new ResponseMess(true, "Successfully"));
           } else {
               return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMess(false, "ERROR"));
           }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMess(false, Constants.SOMETHING_WENT_WRONG));
    }

    public Set<ImageProduct> uploadImage(MultipartFile[] multipartFiles) throws IOException {
        Set<ImageProduct> imageProducts = new HashSet<>();
        System.out.println("Uploading");

        String FOLDER_PATH = "D:/Shoes_Shop/images/";

        if(multipartFiles != null) {
            for (MultipartFile file : multipartFiles) {
                String fileName = file.getOriginalFilename();
                String[] fileNameParts = fileName.split("\\.");
                String extension = fileNameParts[fileNameParts.length - 1];
                String newFileName = fileNameParts[0] + "_" + System.currentTimeMillis() + "_" + UUID.randomUUID().toString() + "." + extension;
                String fileType = file.getContentType();

                String filePath = FOLDER_PATH + newFileName;
                ImageProduct imageProduct = ImageProduct
                        .builder()
                        .name(newFileName)
                        .type(fileType)
                        .filePath(filePath)
                        .build();
                file.transferTo(new File(filePath));
                imageProducts.add(imageProduct);
            }
        } else {
            System.out.println("Khong co anh");
        }
        return imageProducts;
    }

}
