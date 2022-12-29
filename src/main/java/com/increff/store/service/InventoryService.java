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
    public void add(InventoryPojo newPojo)
    {
        InventoryPojo p=null;
        try{
            p = dao.select(newPojo.getId());
        }
        catch(Exception e)
        {

        }
        if(p==null)
        {
            dao.insert(newPojo);
        }
        else{
            update(newPojo);
        }
    }

    @Transactional
    public void remove(InventoryPojo newPojo) throws ApiException
    {
        InventoryPojo p = null;
        try{
            p = dao.select(newPojo.getId());
        }
        catch(Exception e)
        {

        }
        if(p!=null && p.getQuantity() >= newPojo.getQuantity())
        {
            newPojo.setQuantity(newPojo.getQuantity() * -1);
            update(newPojo);
        }
        else{
            throw new ApiException("inventory cannot be reduced");
        }
    }

    @Transactional
    public void delete_id(int id)
    {
        dao.delete(id);
    }

    @Transactional
    public InventoryPojo get(int id)
    {
        return dao.select(id);
    }

    @Transactional
    public List<InventoryPojo> get_all()
    {
        return dao.selectAll();
    }

    @Transactional
    private void update(InventoryPojo newPojo)
    {
        InventoryPojo p = dao.select(newPojo.getId());
        p.setQuantity(p.getQuantity() + newPojo.getQuantity());
    }

}
