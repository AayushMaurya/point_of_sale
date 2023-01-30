package com.increff.store.dto;

import com.increff.store.model.*;
import com.increff.store.pojo.BrandPojo;
import com.increff.store.pojo.InventoryPojo;
import com.increff.store.pojo.OrderPojo;
import com.increff.store.pojo.UserPojo;
import com.increff.store.service.ApiException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.increff.store.dto.DtoUtils.*;
import static com.increff.store.util.GetCurrentDataTime.getCurrentDateTime;
import static org.junit.Assert.assertEquals;

public class DtoUtilsTest extends AbstractUnitTest {
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void convertBrandFormToBrandPojoTest() {
        BrandForm brandForm = new BrandForm();
        brandForm.setBrand("Nestle");
        brandForm.setCategory("Dairy");

        BrandPojo expectedPojo = new BrandPojo();
        expectedPojo.setBrand("Nestle");
        expectedPojo.setCategory("Dairy");

        BrandPojo pojo = convertBrandFormToBrandPojo(brandForm);

        assertEquals(expectedPojo.getBrand(), pojo.getBrand());
        assertEquals(expectedPojo.getCategory(), pojo.getCategory());
    }

    @Test
    public void covertBrandPojoToBrandDataTest() {
        BrandPojo pojo = new BrandPojo();
        pojo.setCategory("dairy");
        pojo.setBrand("nestle");

        BrandData expextedPojo = new BrandData();
        expextedPojo.setBrand("nestle");
        expextedPojo.setCategory("dairy");

        BrandData data = convertBrandPojoToBrandData(pojo);

        assertEquals(expextedPojo.getBrand(), data.getBrand());
        assertEquals(expextedPojo.getCategory(), data.getCategory());
    }

    @Test
    public void convertInventoryPojoToInventoryDataTest() {
        InventoryPojo pojo = new InventoryPojo();
        pojo.setId(2);
        pojo.setQuantity(20);

        InventoryData expectedData = new InventoryData();
        expectedData.setId(2);
        expectedData.setQuantity(20);

        InventoryData data = convertInventoryPojoToInventoryData(pojo);

        assertEquals(expectedData.getId(), data.getId());
        assertEquals(expectedData.getQuantity(), data.getQuantity());
    }

    @Test
    public void convertOrderPojoToOrderDataTest() {
        OrderPojo pojo = new OrderPojo();
        pojo.setOrderCode("qwertyuiop");
        pojo.setStatus("pending");
        pojo.setCustomerName("aayush");
        pojo.setId(2);
        LocalDateTime dateTime = getCurrentDateTime();
        pojo.setPlaceDateTime(null);
        pojo.setCreatedAt(dateTime);

        OrderData data = convertOrderPojoToOrderData(pojo);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

        String expectedDateTime = dateTime.format(dateTimeFormatter);
        Integer expectedId = 2;

        assertEquals(expectedDateTime, data.getCreatedDataTime());
        assertEquals("", data.getPlacedDataTime());
        assertEquals("qwertyuiop", data.getOrderCode());
        assertEquals("aayush", data.getCustomerName());
        assertEquals(expectedId, data.getId());
        assertEquals("pending", data.getStatus());


        pojo.setStatus("Placed");
        pojo.setPlaceDateTime(dateTime);

        data = convertOrderPojoToOrderData(pojo);

        assertEquals(expectedDateTime, data.getPlacedDataTime());
        assertEquals("Placed", data.getStatus());
    }

    @Test
    public void convertBrandPojoToInventoryReportModelTest() {
        BrandPojo pojo = new BrandPojo();
        pojo.setBrand("nestle");
        pojo.setCategory("dairy");
        pojo.setId(2);

        InventoryReportModel data = convertBrandPojoToInventoryReportModel(pojo);

        Integer expectedQuantity = 0;
        Integer expectedId = 2;

        assertEquals(expectedQuantity, data.getQuantity());
        assertEquals("nestle", data.getBrand());
        assertEquals("dairy", data.getCategory());
        assertEquals(expectedId, data.getBrandCategoryId());
    }

    @Test
    public void checkBrandFormTest() throws ApiException {
        BrandForm form = new BrandForm();
        form.setBrand("");
        form.setCategory("test category");

        exceptionRule.expect(ApiException.class);
        exceptionRule.expectMessage("Brand name cannot be empty");
        checkBrandForm(form);

        form.setCategory("");
        form.setBrand("test brand");

        exceptionRule.expect(ApiException.class);
        exceptionRule.expectMessage("Category cannot be empty");
        checkBrandForm(form);
    }

    @Test
    public void normalizeOrderPojoTest() {
        OrderPojo pojo = new OrderPojo();
        pojo.setCustomerName(" Aayush Maurya  ");

        String expectedName = "aayush maurya";
        normalizeOrderPojo(pojo);
        assertEquals(expectedName, pojo.getCustomerName());
    }

    @Test
    public void normalizeBrandPojoTest()
    {
        BrandPojo pojo = new BrandPojo();
        pojo.setBrand(" TesT BranD ");
        pojo.setCategory(" test CaTegory");

        String expectedBrand = "test brand";
        String expectedCategory = "test category";

        normalizeBrandPojo(pojo);
        assertEquals(expectedBrand, pojo.getBrand());
        assertEquals(expectedCategory, pojo.getCategory());
    }

    @Test
    public void checkProductFormTest() throws ApiException
    {
        ProductForm form = new ProductForm();
        form.setBarcode("");
        form.setName("test name");
        form.setBrandName("test brand name");
        form.setCategoryName("test category name");

        exceptionRule.expect(ApiException.class);
        exceptionRule.expectMessage("Barcode cannot be empty");
        checkProductForm(form);

        form.setBarcode("qwertyuiop");
        form.setName("");
        exceptionRule.expect(ApiException.class);
        exceptionRule.expectMessage("Product name cannot be empty");
        checkProductForm(form);

        form.setCategoryName("");
        form.setName("test name");
        exceptionRule.expect(ApiException.class);
        exceptionRule.expectMessage("Category cannot be empty");
        checkProductForm(form);

        form.setBrandName("");
        form.setCategoryName("test category name");
        exceptionRule.expect(ApiException.class);
        exceptionRule.expectMessage("Brand name cannot be empty");
        checkProductForm(form);
    }

    @Test
    public void convertUserPojoToUserDataTest()
    {
        UserPojo pojo = new UserPojo();
        pojo.setEmail("test@email");
        pojo.setRole("test role");
        pojo.setPassword("asdfgh");
        pojo.setId(12);

        UserData data = convertUserPojoToUserData(pojo);

        String expectedEmail = "test@email";
        String expectedRole = "test role";
        Integer testId = 12;

        assertEquals(expectedEmail, data.getEmail());
        assertEquals(expectedRole, data.getRole());
        assertEquals(testId, data.getId());
    }

    @Test
    public void convertUserFormToUserPojoTest()
    {
        UserForm form = new UserForm();
        form.setEmail("test@email");
        form.setPassword("testpassword");

        UserPojo pojo = convertUserFormToUserPojo(form);

        String expectedEmail = "test@email";
        String expectedPassword = "testpassword";
        String expectedRole = "operator";

        assertEquals(expectedEmail, pojo.getEmail());
        assertEquals(expectedPassword, pojo.getPassword());
        assertEquals(expectedRole, pojo.getRole());
    }

    @Test
    public void checkOrderFormTest() throws ApiException
    {
        OrderForm form = new OrderForm();
        form.setCustomerName("");

        exceptionRule.expect(ApiException.class);
        exceptionRule.expectMessage("Customer name cannot be empty");
        checkOrderForm(form);

        form.setCustomerName("very very very long test name");
        exceptionRule.expect(ApiException.class);
        exceptionRule.expectMessage("Customer name cannot be longer than 15 characters");
        checkOrderForm(form);
    }

    @Test
    public void checkUpdateOrderItemFormTest() throws ApiException
    {
        UpdateOrderItemForm form = new UpdateOrderItemForm();
        form.setSellingPrice(12.343);
        form.setQuantity(2);

        checkUpdateOrderItemForm(form);

        Integer expectedQuantity = 2;
        Double expectedSellingPrice = 12.34;

        assertEquals(expectedQuantity, form.getQuantity());
        assertEquals(expectedSellingPrice, form.getSellingPrice());

        form.setQuantity(-1);

        exceptionRule.expect(ApiException.class);
        exceptionRule.expectMessage("Input valid quantity");
        checkUpdateOrderItemForm(form);

        form.setSellingPrice(-12.2);

        exceptionRule.expect(ApiException.class);
        exceptionRule.expectMessage("Input valid Selling Price");
        checkUpdateOrderItemForm(form);
    }
}
