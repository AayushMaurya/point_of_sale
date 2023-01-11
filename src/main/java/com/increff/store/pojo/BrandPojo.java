package com.increff.store.pojo;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@Table(name = "Brand_Table")
public class BrandPojo extends DateAudit{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Size(min = 1)
    @Column(nullable = false, name = "brand_name")
    private String brand;
    @Size(min = 1)
    @Column(nullable = false, name = "category_name")
    private String category;
}
