package com.increff.store.dto;

import com.increff.store.model.BrandData;
import com.increff.store.model.BrandForm;
import com.increff.store.pojo.BrandPojo;
import com.increff.store.service.ApiException;
import com.increff.store.service.BrandService;
import com.increff.store.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BrandDto {

    @Autowired
    BrandService service;

    public void add(BrandForm form) throws ApiException
    {
        BrandPojo p = convert(form);
        normalize(p);
        service.add(p);
    }

    public BrandData get(int id) throws ApiException
    {
        BrandPojo p = service.get(id);
        return convert(p);
    }

    public List<BrandData> get_all()
    {
        List<BrandPojo> list = service.get_all();
        List<BrandData> list2 = new ArrayList<BrandData>();

        for(BrandPojo p: list)
            list2.add(convert(p));

        return list2;
    }

    public void update (int id, BrandForm form) throws ApiException
    {
        BrandPojo p = convert(form);
        normalize(p);
        service.update(id, p);
    }

    private BrandPojo convert(BrandForm form)
    {
        BrandPojo p = new BrandPojo();
        p.setBrand(form.getBrand());
        p.setCategory(form.getCategory());

        return p;
    }

    private BrandData convert(BrandPojo p)
    {
        BrandData brandData = new BrandData();
        brandData.setId(p.getId());
        brandData.setCategory(p.getCategory());
        brandData.setBrand(p.getBrand());
        return brandData;
    }

    protected static void normalize(BrandPojo p) {

        p.setCategory(StringUtil.toLowerCase(p.getCategory()));
        p.setBrand(StringUtil.toLowerCase(p.getBrand()));
    }
}
