package com.increff.store.dto;

import com.increff.store.model.BrandData;
import com.increff.store.model.BrandForm;
import com.increff.store.pojo.BrandPojo;
import com.increff.store.service.ApiException;
import com.increff.store.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.increff.store.dto.DtoUtils.*;

@Service
public class BrandDto {

    @Autowired
    BrandService service;

    public void addBrand(BrandForm form) throws ApiException {
        checkBrandForm(form);
        BrandPojo p = convertBrandFormToBrandPojo(form);
        normalizeBrandPojo(p);
        service.addBrand(p);
    }

    public List<BrandData> getAllBrands() {
        List<BrandPojo> list = service.getAllBrands();
        List<BrandData> list2 = new ArrayList<BrandData>();

        for (BrandPojo p : list)
            list2.add(convertBrandPojoToBrandData(p));

        return list2;
    }

    public void updateBrand(int id, BrandForm form) throws ApiException {
        checkBrandForm(form);
        BrandPojo p = convertBrandFormToBrandPojo(form);
        normalizeBrandPojo(p);
        service.updateBrand(id, p);
    }

}
