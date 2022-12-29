package com.increff.store.dto;

import com.increff.store.model.ProductData;
import com.increff.store.model.ProductForm;
import com.increff.store.pojo.ProductPojo;
import com.increff.store.service.ApiException;
import com.increff.store.service.ProductService;
import com.increff.store.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductDto {
    @Autowired
    private ProductService service;

    public void add(ProductForm form) throws ApiException
    {
        ProductPojo p = convert(form);
        normalize(p);
        service.add(p);
    }

    public ProductData get(int id)
    {
        ProductPojo p = service.get(id);
        return  convert(p);
    }

    public List<ProductData> get_all()
    {
        List<ProductPojo> list1 = service.get_all();
        List<ProductData> list2 = new ArrayList<ProductData>();
        for(ProductPojo p: list1)
            list2.add(convert(p));

        return list2;
    }

    public void update(int id, ProductForm form)
    {
        ProductPojo p = convert(form);
        normalize(p);
        service.update(id, p);
    }

    private ProductPojo convert(ProductForm form)
    {
        ProductPojo p = new ProductPojo();
        p.setBrandCategory(form.getBrandCategory());
        p.setMrp(form.getMrp());
        p.setName(form.getName());
        p.setBarcode(form.getBarcode());
        return p;
    }

    private ProductData convert(ProductPojo p)
    {
        ProductData productData = new ProductData();
        productData.setBrandCategory(p.getBrandCategory());
        productData.setName(p.getName());
        productData.setId(p.getId());
        productData.setBarcode(p.getBarcode());
        productData.setMrp(p.getMrp());

        return productData;
    }

    protected static void normalize(ProductPojo p) {
        p.setName(StringUtil.toLowerCase(p.getName()));
    }

}
