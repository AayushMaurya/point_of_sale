package com.increff.store.dto;

import com.google.protobuf.Api;
import com.increff.store.model.*;
import com.increff.store.pojo.*;
import com.increff.store.service.ApiException;
import com.increff.store.util.StringUtil;

import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class DtoUtils {

    protected static BrandPojo convertBrandFormToBrandPojo(BrandForm form) {
        BrandPojo p = new BrandPojo();
        p.setBrand(form.getBrand());
        p.setCategory(form.getCategory());

        return p;
    }

    protected static BrandData convertBrandPojoToBrandData(BrandPojo pojo) {
        BrandData brandData = new BrandData();
        brandData.setId(pojo.getId());
        brandData.setCategory(pojo.getCategory());
        brandData.setBrand(pojo.getBrand());
        return brandData;
    }

    protected static InventoryData convertInventoryPojoToInventoryData(InventoryPojo p) {
        InventoryData d = new InventoryData();
        d.setId(p.getId());
        d.setQuantity(p.getQuantity());
        return d;
    }

    protected static OrderPojo convertOrderFormToOrderPojo(OrderForm form) {
        OrderPojo orderPojo = new OrderPojo();
        orderPojo.setCustomerName(form.getCustomerName());
        return orderPojo;
    }

    protected static OrderData convertOrderPojoToOrderData(OrderPojo p) {
        OrderData orderData = new OrderData();
        orderData.setId(p.getId());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        orderData.setCreatedDataTime(p.getCreatedDateTime().format(dateTimeFormatter));
        orderData.setPlacedDataTime("");
        if(Objects.equals(p.getStatus(), "Placed"))
            orderData.setPlacedDataTime(p.getPlaceDateTime().format(dateTimeFormatter));
        orderData.setCustomerName(p.getCustomerName());
        orderData.setStatus(p.getStatus());
        orderData.setOrderCode(p.getOrderCode());

        return orderData;
    }

    protected static InventoryReportModel convertBrandPojoToInventoryReportModel(BrandPojo p) {
        InventoryReportModel inventoryReportModel = new InventoryReportModel();

        inventoryReportModel.setBrand(p.getBrand());
        inventoryReportModel.setCategory(p.getCategory());
        inventoryReportModel.setBrandCategoryId(p.getId());
        inventoryReportModel.setQuantity(0);

        return inventoryReportModel;
    }

    protected static void checkBrandForm(BrandForm form) throws ApiException {
        if (StringUtil.isEmpty(form.getBrand())) throw new ApiException("Brand name cannot be empty");
        if (StringUtil.isEmpty(form.getCategory())) throw new ApiException("Category cannot be empty");
    }

    protected static void normalize(OrderPojo p) {
        p.setCustomerName(StringUtil.toLowerCase(p.getCustomerName()));
    }

    protected static void normalizeBrandPojo(BrandPojo p) {
        p.setCategory(StringUtil.toLowerCase(p.getCategory()));
        p.setBrand(StringUtil.toLowerCase(p.getBrand()));
    }

    protected static void checkProductForm(ProductForm form) throws ApiException {
        if (StringUtil.isEmpty(form.getName()))
            throw new ApiException("Product name cannot be empty");
        if (StringUtil.isEmpty(form.getBarcode()))
            throw new ApiException("barcode cannot be empty");
        if (StringUtil.isEmpty(form.getBrandName()))
            throw new ApiException("Brand name cannot be empty");
        if (StringUtil.isEmpty(form.getBrandCategory()))
            throw new ApiException("Category cannot be empty");
    }

    protected static UserData convertUserPojoToUserData(UserPojo p) {
        UserData d = new UserData();
        d.setEmail(p.getEmail());
        d.setRole(p.getRole());
        d.setId(p.getId());
        return d;
    }

    protected static UserPojo convertUserFormToUserPojo(UserForm f) {
        UserPojo p = new UserPojo();
        p.setEmail(f.getEmail());

        p.setRole("operator");
        p.setPassword(f.getPassword());
        return p;
    }

    protected static void checkOrderForm(OrderForm form) throws ApiException {
        if (StringUtil.isEmpty(form.getCustomerName())) throw new ApiException("Customer name cannot be empty");
    }

    protected static DailyReportData convertReportPojoToReportData(DailyReportPojo pojo) {
        DailyReportData dailyReportData = new DailyReportData();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDateTime = pojo.getDate().format(dateTimeFormatter);
        dailyReportData.setDate(formattedDateTime);
        dailyReportData.setTotalRevenue(pojo.getTotalRevenue());
        dailyReportData.setInvoicedItemsCount(pojo.getInvoicedItemsCount());
        dailyReportData.setInvoicedOrderCount(pojo.getInvoicedOrderCount());
        return dailyReportData;
    }

}
