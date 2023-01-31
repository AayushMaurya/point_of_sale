package com.increff.store.dto;

import com.increff.store.dao.OrderDao;
import com.increff.store.model.*;
import com.increff.store.pojo.BrandPojo;
import com.increff.store.pojo.OrderPojo;
import com.increff.store.api.ApiException;
import com.increff.store.api.BrandService;
import com.increff.store.api.OrderService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class OrderDtoTest extends AbstractUnitTest {
    @Autowired
    OrderDto dto;
    @Autowired
    OrderService service;
    @Autowired
    BrandService brandService;
    @Autowired
    ProductDto productDto;
    @Autowired
    InventoryDto inventoryDto;

    @Autowired
    OrderDao orderDao;

    @Autowired
    OrderItemDto orderItemDto;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void createOrderTest() throws ApiException {
        String code = dto.createOrder();

        OrderPojo pojo = service.getOrderByOrderCode(code);

        assertEquals("", pojo.getCustomerName());
        assertEquals("pending", pojo.getStatus());
        assertNull(pojo.getPlaceDateTime());
    }

    @Test
    public void getAllOrdersTest() throws ApiException {
        dto.createOrder();
        dto.createOrder();

        List<OrderData> list = dto.getAllOrders();

        assertEquals(2, list.size());
    }

    @Test
    public void getOrderByIdTest() throws ApiException {
        String code = dto.createOrder();

        OrderData expectedData = dto.getOrderByOrderCode(code);
        Integer id = expectedData.getId();

        OrderData data = dto.getOrderById(id);

        assertEquals(expectedData.getCreatedDataTime(), data.getCreatedDataTime());
        assertEquals(expectedData.getOrderCode(), data.getOrderCode());
        assertEquals(expectedData.getStatus(), data.getStatus());
        assertEquals(expectedData.getPlacedDataTime(), data.getPlacedDataTime());
        assertEquals(expectedData.getId(), data.getId());
        assertEquals(expectedData.getCustomerName(), data.getCustomerName());
    }

    @Test
    public void placeOrderTest() throws ApiException {
        String code = dto.createOrder();
        OrderData data = dto.getOrderByOrderCode(code);

        Integer id = data.getId();

        OrderForm form = new OrderForm();
        form.setCustomerName("test customer");

        exceptionRule.expect(ApiException.class);
        exceptionRule.expectMessage("Add at least one item");
        dto.placeOrder(id, form);

        OrderItemForm orderItemForm = new OrderItemForm();
        orderItemForm.setOrderId(12);
        orderItemForm.setBarCode("qwertyuiop");
        orderItemForm.setSellingPrice(10);
        orderItemForm.setQuantity(2);
        orderItemDto.addOrderItem(orderItemForm);

        exceptionRule.expect(ApiException.class);
        exceptionRule.expectMessage("Order already placed");
        dto.placeOrder(12, form);

        exceptionRule.expect(ApiException.class);
        exceptionRule.expectMessage("Cannot create Invoice for given order");
        dto.placeOrder(id, form);

        addOrderForTest();

    }

//    adds dummy brand for test
    public void addBrandForTest() throws ApiException
    {
        BrandPojo pojo = new BrandPojo();
        pojo.setBrand("test brand");
        pojo.setCategory("test category");

        brandService.addBrand(pojo);
    }

//    adds a dummy product for test
    public void addProductForTest() throws ApiException
    {
        ProductForm form = new ProductForm();
        form.setMrp(12.00);
        form.setName("test name");
        form.setBrandName("test brand");
        form.setCategoryName("test category");
        form.setBarcode("qwertyuiop");

        productDto.addProduct(form);
    }

//    adds a dummy inventory for test
    public void addInventoryForTest() throws ApiException
    {
        InventoryForm inventoryForm = new InventoryForm();
        inventoryForm.setBarcode("qwertyuiop");
        inventoryForm.setQuantity(10);

        inventoryDto.addInventory(inventoryForm);
    }

//    this adds a dummy order for test
    public void addOrderForTest()
    {
        OrderPojo pojo = new OrderPojo();
        pojo.setId(12);
        pojo.setOrderCode("qwertyuiop");
        pojo.setStatus("Placed");
        pojo.setCustomerName("test name");

        orderDao.insert(pojo);
    }
}
