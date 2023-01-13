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
        InventoryPojo pojo = dao.selectById(newPojo.getId());
        if (pojo == null) {
            dao.insert(newPojo);
        } else {
            updateInventory(newPojo);
        }
    }

    @Transactional
    public void reduceInventory(InventoryPojo newPojo) throws ApiException {
        InventoryPojo pojo = dao.selectById(newPojo.getId());

        if (pojo == null)
            throw new ApiException("No inventory found with given Id");

        if (pojo.getQuantity() < newPojo.getQuantity())
            throw new ApiException("Inventory has only " + pojo.getQuantity() + " quantity left");

        newPojo.setQuantity(newPojo.getQuantity() * -1);
        updateInventory(newPojo);
    }

    @Transactional
    public List<InventoryPojo> getAllInventory() {
        return dao.selectAll();
    }

    @Transactional
    private void updateInventory(InventoryPojo newPojo) {
        InventoryPojo pojo = dao.selectById(newPojo.getId());
        pojo.setQuantity(pojo.getQuantity() + newPojo.getQuantity());
    }

}
