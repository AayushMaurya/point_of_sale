package com.increff.store.dto;

import com.increff.store.model.*;
import com.increff.store.pojo.OrderItemPojo;
import com.increff.store.service.ApiException;
import com.increff.store.service.OrderItemService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class OrderItemDtoTest extends AbstractUnitTest{
    @Autowired
    BrandDto brandDto;
    @Autowired
    ProductDto productDto;
    @Autowired
    OrderDto orderDto;
    @Autowired
    OrderItemDto orderItemDto;
    @Autowired
    InventoryDto inventoryDto;
    @Autowired
    OrderItemService orderItemService;

    @Test
    public void addOrderItemTest() throws ApiException
    {
        addBrandForTest();
        addProductForTest();
        addInventoryForTest();

        String orderCode = createOrderForTest();

        OrderItemForm form = new OrderItemForm();
        form.setBarCode("qwertyuiop");
        form.setQuantity(2);
        form.setSellingPrice(10.00);
        form.setOrderId(orderDto.getOrderByOrderCode(orderCode).getId());

        Integer id = orderItemDto.addOrderItem(form);

        OrderItemPojo orderItemPojo = orderItemService.getOrderItemById(id);

        Integer expectedQuantity = 2;
        Double expectedSellingPrice = 10.00;
        assertEquals(expectedQuantity, orderItemPojo.getQuantity());
        assertEquals(expectedSellingPrice, orderItemPojo.getSellingPrice());
    }

    public void addBrandForTest() throws ApiException {
        BrandForm brandForm = new BrandForm();
        brandForm.setBrand("test brand");
        brandForm.setCategory("test category");

        brandDto.addBrand(brandForm);
    }

    public void addProductForTest() throws ApiException {
        ProductForm productForm = new ProductForm();
        productForm.setName("test name");
        productForm.setBarcode("qwertyuiop");
        productForm.setBrandName("test brand");
        productForm.setCategoryName("test category");
        productForm.setMrp(12.00);
        productDto.addProduct(productForm);
    }

    public void addInventoryForTest() throws ApiException
    {
        InventoryForm inventoryForm = new InventoryForm();
        inventoryForm.setBarcode("qwertyuiop");
        inventoryForm.setQuantity(10);
        inventoryDto.addInventory(inventoryForm);
    }

    public String createOrderForTest() throws ApiException
    {
        return orderDto.createOrder();
    }
}
