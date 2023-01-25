package com.increff.store.dto;

import com.increff.store.model.*;
import com.increff.store.pojo.*;
import com.increff.store.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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

    public List<ProductRevenueData> getBrandCategoryWiseReport(DateFilterForm form) throws ApiException {
        List<ProductRevenueData> list1 = new ArrayList<ProductRevenueData>();

        List<BrandPojo> brandPojoList = brandService.getAllBrands();

//        key: productId
        HashMap<Integer, ProductRevenueData> map = new HashMap<>();

//        getting the list of all available products in map
        for (BrandPojo p : brandPojoList) {
            ProductRevenueData productRevenueData = convertBrandPojoToProductRevenueData(p);
            map.put(p.getId(), productRevenueData);
        }

        LocalDateTime startDate;
        LocalDateTime endDate;

//      converting the date into required format
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            startDate = LocalDate.parse(form.getStart(), formatter).atStartOfDay();
            endDate = LocalDate.parse(form.getEnd(), formatter).atTime(23, 59, 59);
        } catch (Exception e) {
            throw new ApiException("Please enter valid start and end date");
        }

        List<OrderPojo> orderPojoList = orderService.selectOrderByDateFilter(startDate, endDate);

//        setting the quantity and total
//          -> order wise filtered on basis of date
        for (OrderPojo e : orderPojoList) {
            Integer orderId = e.getId();
            List<OrderItemPojo> orderItemPojoList = orderItemService.getOrder(orderId);

            for (OrderItemPojo p : orderItemPojoList) {
                Integer brandCategoryId = p.getBrandCategory();
                Integer quantity = map.get(brandCategoryId).getQuantity();
                double total = map.get(brandCategoryId).getTotal();

                map.get(brandCategoryId).setQuantity(quantity + p.getQuantity());
                map.get(brandCategoryId).setTotal(total + p.getQuantity() * p.getSellingPrice());
            }
        }

//        Converting map to list
        for (Map.Entry<Integer, ProductRevenueData> e : map.entrySet())
            list1.add(e.getValue());

        return list1;
    }

    public List<ProductRevenueData> getSalesReport(FilterForm form) throws ApiException
    {
        String brand = form.getBrand();
        String category = form.getCategory();
        List<ProductRevenueData> res = getBrandCategoryWiseReport(form);
        return res.stream().filter(d -> (d.getBrand().equals(brand) || brand.equals(
                "All")) &&
                (d.getCategory().equals(category) || category.equals("All"))).collect(Collectors.toList());
    }

    public List<InventoryReportModel> getInventoryReport() {
        List<BrandPojo> brandPojoList = brandService.getAllBrands();

        HashMap<Integer, InventoryReportModel> map = new HashMap<>();

        for (BrandPojo b : brandPojoList)
            map.put(b.getId(), convertBrandPojoToInventoryReportModel(b));

        List<InventoryPojo> inventoryPojoList = inventoryService.getAllInventory();

        HashMap<Integer, Integer> inventoryMap = new HashMap<>();

//        key: productId value: quantity of that product
        for (InventoryPojo p : inventoryPojoList)
            inventoryMap.put(p.getId(), p.getQuantity());

        List<ProductPojo> productPojoList = productService.getAllProducts();

        for (ProductPojo p : productPojoList) {
            Integer quantity = map.get(p.getBrandCategory()).getQuantity();
            Integer newQuantity = 0;
            if (inventoryMap.containsKey(p.getId()))
                newQuantity = inventoryMap.get(p.getId());

            map.get(p.getBrandCategory()).setQuantity(quantity + newQuantity);
        }

        List<InventoryReportModel> list = new ArrayList<>();

        for (Map.Entry<Integer, InventoryReportModel> e : map.entrySet())
            list.add(e.getValue());

        return list;
    }

}
