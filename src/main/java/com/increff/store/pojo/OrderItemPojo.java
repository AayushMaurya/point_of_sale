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
public class OrderItemPojo {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private int orderId;
    private int productId;
    private int quantity;
    private double sellingPrice;
    private int brandCategory;
}
