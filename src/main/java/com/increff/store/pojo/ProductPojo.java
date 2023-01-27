package com.increff.store.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@Table(name = "product_table")
public class ProductPojo extends AbstractDateAudit {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Size(min = 5)
    @Column(nullable = false)
    private String barcode;
    @Column(nullable = false, name = "brand_category_id")
    private Integer brandCategory;
    @Size(min = 1)
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private double mrp;
}
