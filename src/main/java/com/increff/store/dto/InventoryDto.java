package com.increff.store.dto;

import com.increff.store.model.data.InventoryData;
import com.increff.store.model.form.InventoryForm;
import com.increff.store.pojo.InventoryPojo;
import com.increff.store.pojo.ProductPojo;
import com.increff.store.api.ApiException;
import com.increff.store.api.InventoryService;
import com.increff.store.api.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
        checkInventoryForm(form);
        InventoryPojo p = convertInventoryFormToInventoryPojo(form);
        service.reduceInventory(p);
    }

    public void addInventory(InventoryForm form) throws ApiException
    {
        checkInventoryForm(form);
        InventoryPojo pojo = convertInventoryFormToInventoryPojo(form);
        service.addInventory(pojo);
    }

    @Transactional(rollbackOn = ApiException.class)
    public void updateInventory(InventoryForm form) throws ApiException
    {
        checkInventoryForm(form);
        InventoryPojo pojo = convertInventoryFormToInventoryPojo(form);
        Integer id = pojo.getId();

        InventoryPojo oldPojo = service.getInventoryById(id);

        Integer oldQuantity = oldPojo.getQuantity();

        service.addInventory(pojo);
        pojo.setQuantity(oldQuantity);
        service.reduceInventory(pojo);
    }

    public InventoryPojo convertInventoryFormToInventoryPojo(InventoryForm form) throws ApiException
    {
        InventoryPojo pojo = new InventoryPojo();
//        converting the barcode into product id
        String barcode = form.getBarcode();
        ProductPojo productPojo = productService.getProductByBarcode(barcode);
        pojo.setQuantity(form.getQuantity());
        pojo.setId(productPojo.getId());
        return pojo;
    }

    public static void checkInventoryForm(InventoryForm form) throws ApiException
    {
        if(form.getQuantity() <= 0)
            throw new ApiException("Please input a valid positive quantity");
    }
}
