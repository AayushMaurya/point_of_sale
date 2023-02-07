package com.increff.store.dto;

import com.increff.store.model.*;
import com.increff.store.model.data.*;
import com.increff.store.model.form.*;
import com.increff.store.pojo.*;
import com.increff.store.api.ApiException;
import com.increff.store.util.StringUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

public class DtoUtils {

    protected static BrandPojo convertBrandFormToBrandPojo(BrandForm form) {
        BrandPojo pojo = new BrandPojo();
        pojo.setBrand(form.getBrand());
        pojo.setCategory(form.getCategory());

        return pojo;
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

    protected static OrderData convertOrderPojoToOrderData(OrderPojo p) {
        OrderData orderData = new OrderData();
        orderData.setId(p.getId());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        orderData.setCreatedDataTime(p.getCreatedAt().format(dateTimeFormatter));
        orderData.setPlacedDataTime("");
        if (Objects.equals(p.getStatus(), "Placed"))
            orderData.setPlacedDataTime(p.getPlaceDateTime().format(dateTimeFormatter));
        orderData.setCustomerName(p.getCustomerName());
        orderData.setStatus(p.getStatus());
        orderData.setOrderCode(p.getOrderCode());

        return orderData;
    }

    protected static InventoryReportModel convertBrandPojoToInventoryReportModel(BrandPojo pojo) {
        InventoryReportModel inventoryReportModel = new InventoryReportModel();

        inventoryReportModel.setBrand(pojo.getBrand());
        inventoryReportModel.setCategory(pojo.getCategory());
        inventoryReportModel.setBrandCategoryId(pojo.getId());
        inventoryReportModel.setQuantity(0);

        return inventoryReportModel;
    }

    protected static void checkBrandForm(BrandForm form) throws ApiException {
        if (StringUtil.isEmpty(form.getBrand())) throw new ApiException("Brand name cannot be empty");
        if (StringUtil.isEmpty(form.getCategory())) throw new ApiException("Category cannot be empty");
    }

    protected static void normalizeOrderPojo(OrderPojo p) {
        p.setCustomerName(StringUtil.toLowerCase(p.getCustomerName()));
    }

    protected static void normalizeBrandPojo(BrandPojo p) {
        p.setCategory(StringUtil.toLowerCase(p.getCategory()));
        p.setBrand(StringUtil.toLowerCase(p.getBrand()));
    }

    protected static void checkProductForm(ProductForm form) throws ApiException {
        if (StringUtil.isEmpty(form.getName()))
            throw new ApiException("Product name cannot be empty");
        if (StringUtil.isLonger(form.getName()))
            throw new ApiException("Product name cannot be longer than 15 characters");

        if (StringUtil.isEmpty(form.getBarcode()))
            throw new ApiException("Barcode cannot be empty");
        if (StringUtil.isLonger(form.getBarcode()))
            throw new ApiException("Barcode cannot be longer than 15 characters");

        if (StringUtil.isEmpty(form.getBrandName()))
            throw new ApiException("Brand name cannot be empty");
        if (StringUtil.isLonger(form.getBrandName()))
            throw new ApiException("Brand name cannot be longer than 15 characters");

        if (StringUtil.isEmpty(form.getCategoryName()))
            throw new ApiException("Category cannot be empty");
        if (StringUtil.isLonger(form.getCategoryName()))
            throw new ApiException("Category name cannot be longer than 15 characters");

        if (form.getMrp() <= 0.0)
            throw new ApiException("Mrp should be valid digit");
    }

    protected static UserData convertUserPojoToUserData(UserPojo pojo) {
        UserData data = new UserData();
        data.setEmail(pojo.getEmail());
        data.setRole(pojo.getRole());
        data.setId(pojo.getId());
        return data;
    }

    protected static UserPojo convertUserFormToUserPojo(UserForm form) {
        UserPojo pojo = new UserPojo();
        pojo.setEmail(form.getEmail());
        pojo.setRole(form.getRole());
        pojo.setPassword(form.getPassword());
        return pojo;
    }

    protected static void checkOrderForm(OrderForm form) throws ApiException {
        if (StringUtil.isEmpty(form.getCustomerName()))
            throw new ApiException("Customer name cannot be empty");
        if (StringUtil.isLonger(form.getCustomerName()))
            throw new ApiException("Customer name cannot be longer than 15 characters");
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

    protected static ProductRevenueData convertBrandPojoToProductRevenueData(BrandPojo p) {
        ProductRevenueData productRevenueData = new ProductRevenueData();
        productRevenueData.setId(p.getId());
        productRevenueData.setBrand(p.getBrand());
        productRevenueData.setCategory(p.getCategory());
        productRevenueData.setQuantity(0);
        productRevenueData.setTotal(0.0);
        return productRevenueData;
    }

    protected static void normalize(ProductForm form) {
        form.setName(StringUtil.toLowerCase(form.getName()));
        form.setBrandName(StringUtil.toLowerCase(form.getBrandName()));
        form.setCategoryName(StringUtil.toLowerCase(form.getCategoryName()));
        form.setMrp(StringUtil.normalizeDouble(form.getMrp()));
    }

    protected static void normalize(UpdateProductForm form) {
        form.setName(StringUtil.toLowerCase(form.getName()));
    }

    protected static void checkUpdateOrderItemForm(UpdateOrderItemForm form) throws ApiException {
        if (!StringUtil.isPositive(form.getSellingPrice()))
            throw new ApiException("Input valid Selling Price");
        if (!StringUtil.isPositive(form.getQuantity()))
            throw new ApiException("Input valid quantity");
        form.setSellingPrice(StringUtil.normalizeDouble(form.getSellingPrice()));
    }

    protected static LocalDate stringDateToLocalDate(String date) throws ApiException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(date, formatter);
        } catch (Exception e) {
            throw new ApiException("Please ender date in proper format: yyyy-MM-dd");
        }
    }

    protected static void checkInventoryForm(InventoryForm form) throws ApiException {
        if (form.getQuantity() <= 0)
            throw new ApiException("Please input a valid positive quantity");
    }

    protected static OrderPojo initializeOrderPojo() {
        OrderPojo orderPojo = new OrderPojo();
        orderPojo.setCustomerName("");
        orderPojo.setStatus("pending");
        orderPojo.setPlaceDateTime(null);

//        creating random order code
        orderPojo.setOrderCode(UUID.randomUUID().toString());

        return orderPojo;
    }
}
