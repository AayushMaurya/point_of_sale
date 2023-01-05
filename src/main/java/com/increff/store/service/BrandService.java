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
    public void add(BrandPojo p) throws ApiException
    {
        BrandPojo brandPojo = get_brand_category(p.getBrand(), p.getCategory());
        if(brandPojo!=null)
            throw new ApiException("The given brand category combination already exists");
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

    public BrandPojo get_brand_category(String brand, String category)
    {
        return dao.select_brand_category(brand, category);
    }


    @Transactional
    public void update(int id, BrandPojo newpojo) throws ApiException
    {
        BrandPojo p = dao.select(id);
        String brand = newpojo.getBrand();

//        check if given brand category combination already exists.
        String category = newpojo.getCategory();
        BrandPojo b = get_brand_category(brand, category);
        if(b!=null)
            throw new ApiException("the brand with given brand category already exists");

        p.setBrand(newpojo.getBrand());
        p.setCategory(newpojo.getCategory());
    }
}
