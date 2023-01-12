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
        List<InventoryPojo> list1 = service.getAllInventory();
        List<InventoryData> list2 = new ArrayList<InventoryData>();
        for(InventoryPojo p: list1)
            list2.add(convert(p));

        return list2;
    }

    public void reduceInventory(InventoryForm form) throws ApiException
    {
        InventoryPojo p = convert(form);
        service.reduceInventory(p);
    }

    public void addInventory(InventoryForm form) throws ApiException
    {
        InventoryPojo p = convert(form);
        service.addInventory(p);
    }

    private InventoryPojo convert(InventoryForm form) throws ApiException
    {
        InventoryPojo p = new InventoryPojo();
//        converting the barcode into product id
        String barcode = form.getBarcode();
        ProductPojo productPojo = productService.getProductByBarcode(barcode);
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
