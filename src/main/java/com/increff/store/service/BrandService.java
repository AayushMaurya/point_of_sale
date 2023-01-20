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
    public void addBrand(BrandPojo pojo) throws ApiException
    {
        BrandPojo brandPojo = getByBrandCategory(pojo.getBrand(), pojo.getCategory());
        if(brandPojo!=null)
            throw new ApiException("The given brand category combination already exists");
        dao.insert(pojo);
    }

    @Transactional
    public BrandPojo getByBrandId(Integer id) throws ApiException
    {
        BrandPojo brandPojo =  dao.selectByBrandId(id);
        if(brandPojo == null)
            throw new ApiException("Cannot select a brand with given brand id");
        return brandPojo;
    }

    @Transactional
    public List<BrandPojo> getAllBrands()
    {
        return dao.selectAll();
    }

    public BrandPojo getByBrandCategory(String brand, String category)
    {
        return dao.selectByBrandCategory(brand, category);
    }


    @Transactional
    public void updateBrand(Integer id, BrandPojo newPojo) throws ApiException
    {
//        check if given brand category combination already exists.
        String brand = newPojo.getBrand();
        String category = newPojo.getCategory();
        BrandPojo b = getByBrandCategory(brand, category);
        if(b!=null)
            throw new ApiException("The brand with given brand category already exists");

        BrandPojo p = dao.selectByBrandId(id);

        p.setBrand(newPojo.getBrand());
        p.setCategory(newPojo.getCategory());
    }
}
