package com.increff.store.dto;

import com.increff.store.model.OrderData;
import com.increff.store.model.OrderForm;
import com.increff.store.pojo.OrderPojo;
import com.increff.store.service.ApiException;
import com.increff.store.service.OrderService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrderDtoTest extends AbstractUnitTest {
    @Autowired
    OrderDto dto;
    @Autowired
    OrderService service;
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
        assertEquals(null, pojo.getPlaceDateTime());
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
    }
}
