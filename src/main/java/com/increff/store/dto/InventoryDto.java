package com.increff.store.dto;

import com.increff.store.model.InventoryData;
import com.increff.store.model.InventoryForm;
import com.increff.store.pojo.InventoryPojo;
import com.increff.store.pojo.ProductPojo;
import com.increff.store.service.ApiException;
import com.increff.store.service.InventoryService;
import com.increff.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryDto {

    @Autowired
    InventoryService service;

    @Autowired
    ProductService productService;

    public List<InventoryData> get_all()
    {
        List<InventoryPojo> list1 = service.get_all();
        List<InventoryData> list2 = new ArrayList<InventoryData>();
        for(InventoryPojo p: list1)
            list2.add(convert(p));

        return list2;
    }

    public void remove(InventoryForm form) throws ApiException
    {
        InventoryPojo p = convert(form);
        service.remove(p);
    }

    public void add(InventoryForm form) throws ApiException
    {
        InventoryPojo p = convert(form);
        service.add(p);
    }

    private InventoryPojo convert(InventoryForm form) throws ApiException
    {
        InventoryPojo p = new InventoryPojo();
        String barcode = form.getBarcode();
        ProductPojo productPojo = productService.get_barcode(barcode);
        p.setQuantity(form.getQuantity());
        p.setId(productPojo.getId());
        return p;
    }

    private InventoryData convert(InventoryPojo p)
    {
        InventoryData d =new InventoryData();
        d.setId(p.getId());
        d.setQuantity(p.getQuantity());
        return d;
    }
}
