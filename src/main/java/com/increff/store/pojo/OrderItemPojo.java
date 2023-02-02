package com.increff.store.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "order_items_table", uniqueConstraints = { @UniqueConstraint(name = "UniqueOrderItemIdAndProductId",
        columnNames = { "orderId", "productId" }) })
public class OrderItemPojo extends AbstractDateAudit {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private Integer orderId;
    @Column(nullable = false)
    private Integer productId;
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    private Double sellingPrice;
    @Column(nullable = false)
    private Integer brandCategory;
}
