package com.increff.store.service;

import com.increff.store.dao.BrandDao;
import com.increff.store.pojo.BrandPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BrandService {

    @Autowired
    private BrandDao dao;

    @Transactional
    public void add(BrandPojo p)
    {
        dao.insert(p);
    }

    @Transactional
    public void delete_id(int id)
    {
        dao.delete(id);
    }

    @Transactional
    public BrandPojo get(int id) throws ApiException
    {
        return dao.select(id);
    }

    @Transactional
    public List<BrandPojo> get_all()
    {
        return dao.selectAll();
    }


    @Transactional
    public void update(int id, BrandPojo newpojo) throws ApiException
    {
        BrandPojo p = dao.select(id);
        p.setBrand(newpojo.getBrand());
        p.setCategory(newpojo.getCategory());
    }
}
