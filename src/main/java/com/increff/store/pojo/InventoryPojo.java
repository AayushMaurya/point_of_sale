package com.increff.store.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class InventoryPojo extends DateAudit{
    @Id
    private int id;
    @Column(nullable = false)
    private int quantity;

}
