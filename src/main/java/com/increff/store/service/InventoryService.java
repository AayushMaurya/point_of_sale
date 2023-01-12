package com.increff.store.service;

import com.increff.store.dao.InventoryDao;
import com.increff.store.pojo.InventoryPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class InventoryService {

    @Autowired
    private InventoryDao dao;

    @Transactional
    public void addInventory(InventoryPojo newPojo) {
        InventoryPojo p = dao.selectById(newPojo.getId());
        if (p == null) {
            dao.insert(newPojo);
        } else {
            update(newPojo);
        }
    }

    @Transactional
    public void reduceInventory(InventoryPojo newPojo) throws ApiException {
        InventoryPojo p = dao.selectById(newPojo.getId());

        if (p == null)
            throw new ApiException("No inventory found with given Id");

        if (p.getQuantity() < newPojo.getQuantity())
            throw new ApiException("Inventory has only " + p.getQuantity() + " quantity left");

        newPojo.setQuantity(newPojo.getQuantity() * -1);
        update(newPojo);
    }

    @Transactional
    public List<InventoryPojo> getAllInventory() {
        return dao.selectAll();
    }

    @Transactional
    private void update(InventoryPojo newPojo) {
        InventoryPojo p = dao.selectById(newPojo.getId());
        p.setQuantity(p.getQuantity() + newPojo.getQuantity());
    }

}
