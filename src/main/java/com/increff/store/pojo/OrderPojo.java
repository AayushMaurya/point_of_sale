package com.increff.store.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "Orders_Table")
public class OrderPojo extends AbstractDateAudit {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, name = "created_date_time")
    private String createdDateTime;
    @Column(nullable = false)
    private String customerName;
    @Column(nullable = false, name = "placed_date_time")
    private String placeDateTime;
    @Column(nullable = false)
    private String status;
    @Column(nullable = false, name = "order_code", unique = true)
    private String orderCode;
}
