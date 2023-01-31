package com.increff.store.dto;

import com.increff.store.model.BrandData;
import com.increff.store.model.BrandForm;
import com.increff.store.pojo.BrandPojo;
import com.increff.store.api.ApiException;
import com.increff.store.api.BrandService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class BrandDtoTest extends AbstractUnitTest {
    @Autowired
    BrandDto dto;
    @Autowired
    BrandService service;

    @Test
    public void addBrandTest() throws ApiException {
        BrandForm form = new BrandForm();
        form.setBrand("Test Brand Name");
        form.setCategory("Test Category Name");

        String expectedBrand = "test brand name";
        String expectedCategory = "test category name";

        Integer id = dto.addBrand(form);

        BrandPojo pojo = service.getByBrandId(id);

        assertEquals(expectedBrand, pojo.getBrand());
        assertEquals(expectedCategory, pojo.getCategory());
    }

    @Test
    public void getAllBrandsTest() throws ApiException {
        BrandForm form = new BrandForm();
        form.setBrand("Test Brand Name");
        form.setCategory("Test Category Name");

        dto.addBrand(form);

        form.setBrand("Test Brand Name 2");
        form.setCategory("Test Category Name 2");
        dto.addBrand(form);

        List<BrandData> brandDataList = dto.getAllBrands();

        assertEquals(2, brandDataList.size());
    }

    @Test
    public void updateBrandTest() throws ApiException
    {
        BrandForm form = new BrandForm();
        form.setBrand("Test Brand Name");
        form.setCategory("Test Category Name");

        Integer id = dto.addBrand(form);

        form.setBrand("Test Brand Name 2");
        form.setCategory("Test Category Name 2");
        dto.updateBrand(id, form);

        BrandPojo pojo = service.getByBrandId(id);

        assertEquals("test brand name 2", pojo.getBrand());
        assertEquals("test category name 2", pojo.getCategory());
    }
}
