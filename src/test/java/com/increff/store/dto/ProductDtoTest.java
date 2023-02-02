package com.increff.store.dto;

import com.increff.store.model.form.BrandForm;
import com.increff.store.model.data.ProductData;
import com.increff.store.model.form.ProductForm;
import com.increff.store.api.ApiException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductDtoTest extends AbstractUnitTest{
    @Autowired
    ProductDto dto;

    @Autowired
    BrandDto brandDto;
    @Test
    public void addProductTest() throws ApiException
    {
        ProductForm form = new ProductForm();
        form.setBarcode("test barcode");
        form.setCategoryName("category Name");
        form.setName("product Name");
        form.setBrandName("  brand name");
        form.setMrp(12.763);

        BrandForm brandForm = new BrandForm();
        brandForm.setCategory("category name");
        brandForm.setBrand("brand name");
        brandDto.addBrand(brandForm);

        Integer id = dto.addProduct(form);

        ProductData data = dto.getProductById(id);

        String expectedBrandName = "brand name";
        String expectedCategoryName = "category name";
        String expectedName = "product name";
        Double expectedMrp = 12.76;
        String expectedBarcode = "test barcode";

        assertEquals(expectedBarcode, data.getBarcode());
        assertEquals(expectedName, data.getName());
        assertEquals(expectedBrandName, data.getBrand());
        assertEquals(expectedCategoryName, data.getCategory());
    }

    @Test
    public void getAllProductsTest() throws ApiException
    {
        BrandForm brandForm = new BrandForm();
        brandForm.setCategory("category name");
        brandForm.setBrand("brand name");
        brandDto.addBrand(brandForm);

        ProductForm form = new ProductForm();
        form.setBarcode("test barcode");
        form.setCategoryName("category Name");
        form.setName("product Name");
        form.setBrandName("  brand name");
        form.setMrp(12.763);

        dto.addProduct(form);

        form.setBarcode("test barcode2");
        form.setCategoryName("category Name");
        form.setName("product Name2");
        form.setBrandName("  brand name");
        form.setMrp(12.763);

        dto.addProduct(form);

        List<ProductData> list = dto.getAllProducts();

        assertEquals(2, list.size());
    }

    @Test
    public void updateProductTest() throws ApiException
    {
        BrandForm brandForm = new BrandForm();
        brandForm.setCategory("category name");
        brandForm.setBrand("brand name");
        brandDto.addBrand(brandForm);

        ProductForm form = new ProductForm();
        form.setBarcode("test barcode");
        form.setCategoryName("category Name");
        form.setName("product Name");
        form.setBrandName("  brand name");
        form.setMrp(12.763);

        Integer id = dto.addProduct(form);

        form.setName("changed name");

        dto.updateProduct(id, form);

        ProductData data = dto.getProductById(id);

        String expectedBrandName = "brand name";
        String expectedCategoryName = "category name";
        String expectedName = "changed name";
        String expectedBarcode = "test barcode";

        assertEquals(expectedBarcode, data.getBarcode());
        assertEquals(expectedName, data.getName());
        assertEquals(expectedBrandName, data.getBrand());
        assertEquals(expectedCategoryName, data.getCategory());
    }
}
