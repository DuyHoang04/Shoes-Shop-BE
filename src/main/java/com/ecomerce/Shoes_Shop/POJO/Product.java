package com.ecomerce.Shoes_Shop.POJO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@NamedQuery(name = "Product.getProductAdmin", query = "select new com.ecomerce.Shoes_Shop.Wrapper.ProductWrapper(p.id,p.name,p.description,p.price,p.status,p.productImages,c.name) from Product p JOIN p.brand c")

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    private String name;

    private String description;

    private Double price;

    private String status;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "image_products",
            joinColumns = {
                    @JoinColumn(name = "product_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "image_id")
            }
    )
    private Set<ImageProduct> productImages;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    private String tag;
    @Column(name = "rating")
    private Double rating = 0.0;

    @Column(name = "num_review")
    private Integer numReviews = 0;

//    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.ALL)
//    @JsonIgnoreProperties("product")
        @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
        @JoinTable(name = "review_products",
        joinColumns = {
                @JoinColumn(name = "product_id")
        },
        inverseJoinColumns = {
                @JoinColumn(name = "review_id")
        }
         )
      private Set<Review> reviews;

}
