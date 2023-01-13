package com.increff.store.dto;

import com.increff.store.model.*;
import com.increff.store.pojo.*;
import com.increff.store.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.increff.store.dto.DtoUtils.*;

@Service
public class RevenueDto {

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    OrderService orderService;

    @Autowired
    ProductService productService;

    @Autowired
    BrandService brandService;
    @Autowired
    InventoryService inventoryService;

    public List<ProductRevenueData> getProductWiseReport(DateFilterForm form) throws ApiException
    {
        List<ProductRevenueData> list1 = new ArrayList<ProductRevenueData>();

        List<ProductPojo> productPojoList = productService.getAllProducts();

//        key: productId
        HashMap<Integer, ProductRevenueData> map = new HashMap<>();

//        getting the list of all available products in map
        for(ProductPojo p: productPojoList) {
            ProductRevenueData productRevenueData = convert(p);
            map.put(p.getId(), productRevenueData);
        }
//      converting the date into required formate
        String startDate = correctFormat(form.getStart()) + " 00:00:00";
        String endDate = correctFormat(form.getEnd()) + " 23:59:59";

        List<OrderPojo> orderPojoList = orderService.selectOrderByDateFilter(startDate, endDate);

//        setting the quantity and total
//          -> order wise filtered on basis of date
        for(OrderPojo e: orderPojoList)
        {
            int orderId = e.getId();
            List<OrderItemPojo> orderItemPojoList = orderItemService.getOrder(orderId);

            for(OrderItemPojo p: orderItemPojoList)
            {
                int productId = p.getProductId();
                int quantity = map.get(productId).getQuantity();
                double total = map.get(productId).getTotal();

                map.get(productId).setQuantity(quantity + p.getQuantity());
                map.get(productId).setTotal(total + p.getQuantity()*p.getSellingPrice());
            }
        }

//        Converting map to list
        for (Map.Entry<Integer, ProductRevenueData> e: map.entrySet())
            list1.add(e.getValue());

        return list1;
    }

    public List<BrandRevenueData> getBrandReport(DateFilterForm form) throws ApiException {
        List<BrandRevenueData> res = new ArrayList<>();

//        key: brand name
        HashMap<String, BrandRevenueData> map = new HashMap<>();

        List<ProductRevenueData> list1 = getProductWiseReport(form);

        for(ProductRevenueData p: list1)
        {
            if(map.containsKey(p.getBrand()))
            {
                int quantity = map.get(p.getBrand()).getQuantity();
                double total = map.get(p.getBrand()).getTotal();

                map.get(p.getBrand()).setQuantity(quantity + p.getQuantity());
                map.get(p.getBrand()).setTotal(total + p.getTotal());
            }
            else{
                BrandRevenueData b = new BrandRevenueData();
                b.setBrand(p.getBrand());
                b.setTotal(p.getTotal());
                b.setQuantity(p.getQuantity());

                map.put(p.getBrand(), b);
            }
        }

        for(Map.Entry<String, BrandRevenueData> e: map.entrySet())
            res.add(e.getValue());

        return res;
    }

    public List<CategoryRevenueData> getCategoryReport(DateFilterForm form) throws ApiException {
        List<CategoryRevenueData> res = new ArrayList<>();

        HashMap<String, CategoryRevenueData> map = new HashMap<>();

        List<ProductRevenueData> list1 = getProductWiseReport(form);

        for(ProductRevenueData p: list1)
        {
            if(map.containsKey(p.getCategory()))
            {
                String key = p.getCategory();
                int quantity = map.get(key).getQuantity();
                double total = map.get(key).getTotal();

                map.get(key).setQuantity(quantity + p.getQuantity());
                map.get(key).setTotal(total + p.getTotal());
            }
            else{
                CategoryRevenueData b = new CategoryRevenueData();
                b.setCategory(p.getCategory());
                b.setTotal(p.getTotal());
                b.setQuantity(p.getQuantity());

                map.put(p.getCategory(), b);
            }
        }

        for(Map.Entry<String, CategoryRevenueData> e: map.entrySet())
            res.add(e.getValue());


        return res;
    }

    private ProductRevenueData convert(ProductPojo p) throws ApiException
    {
        ProductRevenueData productRevenueData = new ProductRevenueData();
        productRevenueData.setId(p.getId());
        productRevenueData.setBarcode(p.getBarcode());
        productRevenueData.setMrp(p.getMrp());
        productRevenueData.setName(p.getName());
        productRevenueData.setQuantity(0);
        productRevenueData.setTotal(0);

        int brandCategoryId = p.getBrandCategory();
        BrandPojo brandPojo = brandService.getByBrandId(brandCategoryId);

        productRevenueData.setBrand(brandPojo.getBrand());
        productRevenueData.setCategory(brandPojo.getCategory());

        return productRevenueData;
    }

    String correctFormat(String date)
    {
        String res = date.replace('-', '/');
        return res;
    }

    public List<InventoryReportModel> getInventoryReport() {
        List<BrandPojo> brandPojoList = brandService.getAllBrands();

        HashMap<Integer, InventoryReportModel> map = new HashMap<>();

        for(BrandPojo b: brandPojoList)
            map.put(b.getId(), convertBrandPojoToInventoryReportModel(b));

        List<InventoryPojo> inventoryPojoList = inventoryService.getAllInventory();

        HashMap<Integer, Integer> inventoryMap = new HashMap<>();

//        key: productId value: quantity of that product
        for(InventoryPojo p: inventoryPojoList)
            inventoryMap.put(p.getId(), p.getQuantity());

        List<ProductPojo> productPojoList = productService.getAllProducts();

        for(ProductPojo p: productPojoList)
        {
            int quantity = map.get(p.getBrandCategory()).getQuantity();
            int nquantity = 0;
            if(inventoryMap.containsKey(p.getId()))
                nquantity = inventoryMap.get(p.getId());

            map.get(p.getBrandCategory()).setQuantity(quantity + nquantity);
        }

        List<InventoryReportModel> list = new ArrayList<>();

        for(Map.Entry<Integer, InventoryReportModel> e: map.entrySet())
            list.add(e.getValue());

        return list;
    }

}
