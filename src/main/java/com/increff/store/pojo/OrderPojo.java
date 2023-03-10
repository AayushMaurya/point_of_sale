package com.increff.store.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class OrderPojo {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String createdDateTime;
    private String customerName;
    private String placeDateTime;
    private String status = "";
}
