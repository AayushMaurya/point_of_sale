package com.increff.store.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "Brand_Table")
public class BrandPojo extends DateAudit{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, name = "brand_name")
    private String brand;
    @Column(nullable = false, name = "category_name")
    private String category;
}
