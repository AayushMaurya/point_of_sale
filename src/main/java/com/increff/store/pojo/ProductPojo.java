package com.increff.store.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "Product_Table")
public class ProductPojo extends DateAudit{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String barcode;
    @Column(nullable = false, name = "brand_category_id")
    private Integer brandCategory;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private double mrp;
}
