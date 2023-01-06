package com.increff.store.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "Inventory_Table")
public class InventoryPojo extends DateAudit{
    @Id
    @Column(name = "product_id")
    private Integer id;
    @Column(nullable = false)
    private Integer quantity;

}
