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
import java.util.HashMap;
import java.util.List;

import static com.increff.store.dto.DtoUtils.*;

@Service
public class InventoryDto {

    @Autowired
    InventoryService service;

    @Autowired
    ProductService productService;

    public List<InventoryData> getAllInventory()
    {
        List<InventoryPojo> list1 = service.getAllInventory();
        List<InventoryData> list2 = new ArrayList<InventoryData>();
        for(InventoryPojo p: list1)
            list2.add(convertInventoryPojoToInventoryData(p));

//        setting the barcode;
        List<ProductPojo> productPojoList = productService.getAllProducts();

        HashMap<Integer, String> map = new HashMap<>();
        for(ProductPojo p: productPojoList)
            map.put(p.getId(), p.getBarcode());

        for(InventoryData d: list2)
            d.setBarcode(map.get(d.getId()));

        return list2;
    }

    public void reduceInventory(InventoryForm form) throws ApiException
    {
        InventoryPojo p = convertInventoryFormToInventoryPojo(form);
        service.reduceInventory(p);
    }

    public void addInventory(InventoryForm form) throws ApiException
    {
        InventoryPojo p = convertInventoryFormToInventoryPojo(form);
        service.addInventory(p);
    }

    public InventoryPojo convertInventoryFormToInventoryPojo(InventoryForm form) throws ApiException
    {
        InventoryPojo p = new InventoryPojo();
//        converting the barcode into product id
        String barcode = form.getBarcode();
        ProductPojo productPojo = productService.getProductByBarcode(barcode);
        p.setQuantity(form.getQuantity());
        p.setId(productPojo.getId());
        return p;
    }

}
