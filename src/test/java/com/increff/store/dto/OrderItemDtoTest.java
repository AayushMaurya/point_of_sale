package com.increff.store.dto;

import com.increff.store.dao.OrderDao;
import com.increff.store.model.data.OrderItemData;
import com.increff.store.model.form.BrandForm;
import com.increff.store.model.form.InventoryForm;
import com.increff.store.model.form.OrderItemForm;
import com.increff.store.model.form.ProductForm;
import com.increff.store.pojo.OrderItemPojo;
import com.increff.store.pojo.OrderPojo;
import com.increff.store.api.ApiException;
import com.increff.store.api.OrderItemService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class OrderItemDtoTest extends AbstractUnitTest {
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

    @Autowired
    OrderDao orderDao;

    @Test
    public void addOrderItemTest() throws ApiException {
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

    //    test if an product is already is in order item list
    @Test
    public void addOrderItemTestWithExistingOrderItem() throws ApiException {
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
        orderItemDto.addOrderItem(form);

        OrderItemPojo orderItemPojo = orderItemService.getOrderItemById(id);

        Integer expectedQuantity = 4;
        Double expectedSellingPrice = 10.00;
        assertEquals(expectedQuantity, orderItemPojo.getQuantity());
        assertEquals(expectedSellingPrice, orderItemPojo.getSellingPrice());
    }

    @Test
    public void orderItemPojoToOrderItemData() throws ApiException {
        addBrandForTest();
        Integer productId = addProductForTest();

        OrderItemPojo orderItemPojo = new OrderItemPojo();
        orderItemPojo.setQuantity(2);
        orderItemPojo.setOrderId(12);
        orderItemPojo.setSellingPrice(20.00);
        orderItemPojo.setProductId(productId);

        OrderItemData data = orderItemDto.convertOrderItemPojoToOrderItemDate(orderItemPojo);

        assertEquals(orderItemPojo.getQuantity(), data.getQuantity());
        assertEquals(orderItemPojo.getOrderId(), data.getOrderId());
        assertEquals(orderItemPojo.getProductId(), data.getProductId());
    }

    @Test
    public void checkIfOrderPlacedTest() throws ApiException {
        OrderPojo orderPojo = new OrderPojo();

        orderPojo.setOrderCode("qwertyuop");
        orderPojo.setStatus("Placed");
        orderPojo.setCustomerName("test name");
        orderPojo.setPlaceDateTime(LocalDateTime.now());

        Integer id = orderDao.insert(orderPojo);

        Boolean check = orderItemDto.checkIfOrderPlaced(id);
        assertEquals(true, check);

        orderPojo.setStatus("pending");
        id = orderDao.insert(orderPojo);

        check = orderItemDto.checkIfOrderPlaced(id);
        assertEquals(false, check);
    }

    //    add dummy brand for test
    public void addBrandForTest() throws ApiException {
        BrandForm brandForm = new BrandForm();
        brandForm.setBrand("test brand");
        brandForm.setCategory("test category");

        brandDto.addBrand(brandForm);
    }

    //    add dummy product for test
    public Integer addProductForTest() throws ApiException {
        ProductForm productForm = new ProductForm();
        productForm.setName("test name");
        productForm.setBarcode("qwertyuiop");
        productForm.setBrandName("test brand");
        productForm.setCategoryName("test category");
        productForm.setMrp(12.00);
        return productDto.addProduct(productForm);
    }

    //    add dummy inventory for test
    public void addInventoryForTest() throws ApiException {
        InventoryForm inventoryForm = new InventoryForm();
        inventoryForm.setBarcode("qwertyuiop");
        inventoryForm.setQuantity(10);
        inventoryDto.addInventory(inventoryForm);
    }

    public String createOrderForTest() throws ApiException {
        return orderDto.createOrder();
    }
}
