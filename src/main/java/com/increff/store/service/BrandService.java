package com.increff.store.service;

import com.increff.store.dao.BrandDao;
import com.increff.store.pojo.BrandPojo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional(rollbackOn = ApiException.class)
public class BrandService {

    @Autowired
    private BrandDao dao;

    private static Logger logger = Logger.getLogger(BrandService.class);

    public void addBrand(BrandPojo pojo) throws ApiException {
        BrandPojo brandPojo = getByBrandCategory(pojo.getBrand(), pojo.getCategory());
        if (brandPojo != null)
            throw new ApiException("The given brand category combination already exists");
        try {
            dao.insert(pojo);
        } catch (Exception e) {
            logger.error(e);
            throw new ApiException("Cannot add given brand");
        }
    }

    public BrandPojo getByBrandId(Integer id) throws ApiException {
        BrandPojo brandPojo = dao.selectByBrandId(id);
        if (brandPojo == null)
            throw new ApiException("Cannot select a brand with given brand id");
        return brandPojo;
    }

    public List<BrandPojo> getAllBrands() {
        return dao.selectAll();
    }

    public BrandPojo getByBrandCategory(String brand, String category) {
        return dao.selectByBrandCategory(brand, category);
    }

    public void updateBrand(Integer id, BrandPojo newPojo) throws ApiException {
//        check if given brand category combination already exists.
        String brand = newPojo.getBrand();
        String category = newPojo.getCategory();
        BrandPojo b = getByBrandCategory(brand, category);
        if (b != null && b.getId() != id)
            throw new ApiException("The brand with given brand category already exists");

        try{
            BrandPojo p = dao.selectByBrandId(id);

            p.setBrand(newPojo.getBrand());
            p.setCategory(newPojo.getCategory());
        }
        catch (Exception e)
        {
            logger.error(e);
            throw new ApiException("Cannot update the given brand");
        }
    }
}
