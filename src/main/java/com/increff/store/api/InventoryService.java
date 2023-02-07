package com.increff.store.api;

import com.increff.store.dao.InventoryDao;
import com.increff.store.pojo.InventoryPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional(rollbackOn = ApiException.class)
public class InventoryService {

    @Autowired
    private InventoryDao dao;

    public void addInventory(InventoryPojo newPojo) throws ApiException {
        InventoryPojo pojo = dao.selectById(newPojo.getId());
        if (pojo == null)
            dao.insert(newPojo);
        else
            updateInventory(newPojo);
    }

    public void reduceInventory(InventoryPojo newPojo) throws ApiException {
        InventoryPojo pojo = dao.selectById(newPojo.getId());

        if (pojo == null)
            throw new ApiException("No inventory found with given Id");

        if (pojo.getQuantity() < newPojo.getQuantity())
            throw new ApiException("Inventory has only " + pojo.getQuantity() + " quantity left");

        newPojo.setQuantity(newPojo.getQuantity() * -1);
        updateInventory(newPojo);
    }

    public List<InventoryPojo> getAllInventory() {
        return dao.selectAll();
    }

    public InventoryPojo getInventoryById(Integer id) throws ApiException {
        InventoryPojo pojo = dao.selectById(id);
        if (pojo == null)
            throw new ApiException("Inventory with given id does not exist ");
        return pojo;
    }

    private void updateInventory(InventoryPojo newPojo) throws ApiException {
        InventoryPojo pojo = dao.selectById(newPojo.getId());
        if (pojo == null)
            throw new ApiException("Cannot update inventory");
        pojo.setQuantity(pojo.getQuantity() + newPojo.getQuantity());
    }
}
