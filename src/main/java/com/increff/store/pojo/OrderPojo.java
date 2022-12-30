package com.increff.store.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class OrderPojo {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String createdDateTime;
    @Column(nullable = false)
    private String customerName;
    @Column(nullable = false)
    private String placeDateTime;
    @Column(nullable = false)
    private String status;
}
