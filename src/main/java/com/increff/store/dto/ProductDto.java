package com.increff.store.dto;

import com.increff.store.model.data.ProductData;
import com.increff.store.model.form.ProductForm;
import com.increff.store.model.form.UpdateProductForm;
import com.increff.store.pojo.BrandPojo;
import com.increff.store.pojo.ProductPojo;
import com.increff.store.api.ApiException;
import com.increff.store.api.BrandService;
import com.increff.store.api.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.increff.store.dto.DtoUtils.checkProductForm;
import static com.increff.store.dto.DtoUtils.normalize;

@Service
public class ProductDto {
    @Autowired
    private ProductService service;

    @Autowired
    private BrandService brandService;

    public Integer addProduct(ProductForm form) throws ApiException {
        checkProductForm(form);
        normalize(form);
        ProductPojo pojo = convertProductFormToProductPojo(form);
        return service.addProduct(pojo);
    }

    public ProductData getProductById(Integer productId) throws ApiException {
        ProductPojo pojo = service.getProductById(productId);
        return convertProductPojoToProductData(pojo);
    }

    public List<ProductData> getAllProducts() throws ApiException {
        List<ProductPojo> list1 = service.getAllProducts();
        List<ProductData> list2 = new ArrayList<ProductData>();
        for (ProductPojo p : list1)
            list2.add(convertProductPojoToProductData(p));

        return list2;
    }

    public void updateProduct(Integer productId, UpdateProductForm form) throws ApiException {
        normalize(form);
        ProductPojo pojo = new ProductPojo();
        pojo.setName(form.getName());
        pojo.setMrp(form.getMrp());
        service.updateProduct(productId, pojo);
    }

    private ProductPojo convertProductFormToProductPojo(ProductForm form) throws ApiException {
        ProductPojo pojo = new ProductPojo();

//        converting the unique brand category combination to brand category id
        String brandName = form.getBrandName();
        String brandCategory = form.getCategoryName();
        BrandPojo brandPojo = brandService.getByBrandCategory(brandName, brandCategory);
        if (brandPojo == null)
            throw new ApiException("No such brand category combination found");

        pojo.setBrandCategoryId(brandPojo.getId());
        pojo.setMrp(form.getMrp());
        pojo.setName(form.getName());
        pojo.setBarcode(form.getBarcode());

        return pojo;
    }

    private ProductData convertProductPojoToProductData(ProductPojo pojo) throws ApiException {
        ProductData productData = new ProductData();
        productData.setBrandCategory(pojo.getBrandCategoryId());
        BrandPojo brandPojo = brandService.getByBrandId(pojo.getBrandCategoryId());
        productData.setBrand(brandPojo.getBrand());
        productData.setCategory(brandPojo.getCategory());
        productData.setName(pojo.getName());
        productData.setId(pojo.getId());
        productData.setBarcode(pojo.getBarcode());
        productData.setMrp(pojo.getMrp());

        return productData;
    }
}
