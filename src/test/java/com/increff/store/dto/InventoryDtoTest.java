package com.increff.store.dto;

import com.increff.store.model.BrandForm;
import com.increff.store.model.InventoryData;
import com.increff.store.model.InventoryForm;
import com.increff.store.model.ProductForm;
import com.increff.store.pojo.InventoryPojo;
import com.increff.store.service.ApiException;
import com.increff.store.service.InventoryService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class InventoryDtoTest extends AbstractUnitTest {
    @Autowired
    BrandDto brandDto;
    @Autowired
    ProductDto productDto;
    @Autowired
    InventoryDto inventoryDto;
    @Autowired
    InventoryService service;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void addInventoryTest() throws ApiException {
        addBrandForTest();
        Integer id = addProductForTest();

        InventoryForm inventoryForm = new InventoryForm();
        inventoryForm.setBarcode("qwertyuiop");
        inventoryForm.setQuantity(12);

        inventoryDto.addInventory(inventoryForm);

        InventoryPojo pojo = service.getInventoryById(id);

        Integer expectedQuantity = 12;
        assertEquals(expectedQuantity, pojo.getQuantity());

        inventoryDto.addInventory(inventoryForm);

        pojo = service.getInventoryById(id);

        expectedQuantity = 24;
        assertEquals(expectedQuantity, pojo.getQuantity());
    }

    @Test
    public void reduceInventoryTest() throws ApiException {
        addBrandForTest();
        Integer id = addProductForTest();

        InventoryForm inventoryForm = new InventoryForm();
        inventoryForm.setBarcode("qwertyuiop");
        inventoryForm.setQuantity(12);

        inventoryDto.addInventory(inventoryForm);

        inventoryForm.setBarcode("qwertyuiop");
        inventoryForm.setQuantity(10);
        inventoryDto.reduceInventory(inventoryForm);

        InventoryPojo pojo = service.getInventoryById(id);

        Integer expectedQuantity = 2;
        assertEquals(expectedQuantity, pojo.getQuantity());

        inventoryForm.setQuantity(14);

        exceptionRule.expect(ApiException.class);
        inventoryDto.reduceInventory(inventoryForm);
    }

    @Test
    public void convertInventoryFormToInventoryPojo() throws ApiException {
        addBrandForTest();

        Integer id = addProductForTest();

        InventoryForm form = new InventoryForm();
        form.setQuantity(12);
        form.setBarcode("qwertyuiop");

        InventoryPojo pojo = inventoryDto.convertInventoryFormToInventoryPojo(form);

        Integer expectedQunatity = 12;
        assertEquals(id, pojo.getId());
        assertEquals(expectedQunatity, pojo.getQuantity());

        form.setBarcode("qwertyuiol");

        exceptionRule.expect(ApiException.class);
        exceptionRule.expectMessage("Cannot find product with given bar code");
        inventoryDto.convertInventoryFormToInventoryPojo(form);
    }

    @Test
    public void getAllInventoryTest() throws ApiException {
        addBrandForTest();
        addProductForTest();

        InventoryForm inventoryForm = new InventoryForm();
        inventoryForm.setBarcode("qwertyuiop");
        inventoryForm.setQuantity(12);

        inventoryDto.addInventory(inventoryForm);
        inventoryDto.addInventory(inventoryForm);

        List<InventoryData> inventoryDataList = inventoryDto.getAllInventory();

        assertEquals(1, inventoryDataList.size());

        ProductForm productForm = new ProductForm();
        productForm.setName("test name");
        productForm.setBarcode("qwertyuop");
        productForm.setMrp(20.00);
        productForm.setBrandName("test brand");
        productForm.setCategoryName("test category");

        productDto.addProduct(productForm);

        inventoryForm.setBarcode("qwertyuop");
        inventoryForm.setQuantity(12);

        inventoryDto.addInventory(inventoryForm);

        inventoryDataList = inventoryDto.getAllInventory();

        assertEquals(2, inventoryDataList.size());
    }

    public void addBrandForTest() throws ApiException {
        BrandForm brandForm = new BrandForm();
        brandForm.setBrand("test brand");
        brandForm.setCategory("test category");

        brandDto.addBrand(brandForm);
    }

    public Integer addProductForTest() throws ApiException {
        ProductForm productForm = new ProductForm();
        productForm.setName("test name");
        productForm.setBarcode("qwertyuiop");
        productForm.setMrp(20.00);
        productForm.setBrandName("test brand");
        productForm.setCategoryName("test category");

        return productDto.addProduct(productForm);
    }
}
