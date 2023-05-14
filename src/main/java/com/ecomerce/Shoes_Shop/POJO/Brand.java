package com.ecomerce.Shoes_Shop.POJO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@NamedQuery(name = "Brand.getAllBrand", query="select b from Brand b where b.brandId in (select p.brand from Product p)")

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "brand")
public class Brand implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="brandId")
    private Integer brandId;

    @Column(name = "name")
    private String name;

//    @OneToMany(mappedBy = "brand")
//    private Set<Product> products;

}
