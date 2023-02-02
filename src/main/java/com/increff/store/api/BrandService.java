package com.increff.store.api;

import com.increff.store.dao.BrandDao;
import com.increff.store.pojo.BrandPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional(rollbackOn = ApiException.class)
public class BrandService {

    @Autowired
    private BrandDao dao;

    public Integer addBrand(BrandPojo pojo) throws ApiException {
        BrandPojo brandPojo = getByBrandCategory(pojo.getBrand(), pojo.getCategory());
        if (brandPojo != null)
            throw new ApiException("The given brand category combination already exists");
        return dao.insert(pojo);
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
        BrandPojo brandPojo = getByBrandCategory(newPojo.getBrand(), newPojo.getCategory());
        if (brandPojo != null && brandPojo.getId() != id)
            throw new ApiException("The brand with given brand category already exists");

        BrandPojo p = dao.selectByBrandId(id);

        p.setBrand(newPojo.getBrand());
        p.setCategory(newPojo.getCategory());
    }
}
