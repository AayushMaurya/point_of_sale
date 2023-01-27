package com.increff.store.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "inventory_table")
public class InventoryPojo extends AbstractDateAudit {
    @Id
    @Column(name = "product_id")
    private Integer id;
    @Column(nullable = false)
    private Integer quantity;

}
