package com.increff.store.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class ProductPojo extends DateAudit{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String barcode;
    @Column(nullable = false)
    private int brandCategory;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private double mrp;
}
