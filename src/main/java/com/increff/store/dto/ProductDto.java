package com.increff.store.dto;

import com.increff.store.model.ProductData;
import com.increff.store.model.ProductForm;
import com.increff.store.pojo.BrandPojo;
import com.increff.store.pojo.ProductPojo;
import com.increff.store.service.ApiException;
import com.increff.store.service.BrandService;
import com.increff.store.service.ProductService;
import com.increff.store.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductDto {
    @Autowired
    private ProductService service;

    @Autowired
    private BrandService brandService;

    public void add(ProductForm form) throws ApiException
    {
        isOk(form);
        ProductPojo p = convert(form);
        normalize(p);
        service.addProduct(p);
    }

    public ProductData get(int id) throws ApiException
    {
        ProductPojo p = service.getProductById(id);
        return convert(p);
    }

    public List<ProductData> get_all() throws ApiException
    {
        List<ProductPojo> list1 = service.getAllProducts();
        List<ProductData> list2 = new ArrayList<ProductData>();
        for(ProductPojo p: list1)
            list2.add(convert(p));

        return list2;
    }

    public void update(int id, ProductForm form) throws ApiException
    {
        ProductPojo p = convert(form);
        normalize(p);
        service.update(id, p);
    }

    private ProductPojo convert(ProductForm form) throws  ApiException
    {
        ProductPojo p = new ProductPojo();

//        converting the unique brand category combination to brand category id
        String brandName = form.getBrandName();
        String brandCategory = form.getBrandCategory();
        BrandPojo brandPojo = brandService.getByBrandCategory(brandName, brandCategory);
        if(brandPojo == null)
            throw new ApiException("no such brand category combination found");

        p.setBrandCategory(brandPojo.getId());
        p.setMrp(form.getMrp());
        p.setName(form.getName());
        p.setBarcode(form.getBarcode());

        return p;
    }

    private ProductData convert(ProductPojo p) throws ApiException
    {
        ProductData productData = new ProductData();
        productData.setBrandCategory(p.getBrandCategory());
        BrandPojo brandPojo = brandService.getByBrandId(p.getBrandCategory());
        productData.setBrand(brandPojo.getBrand());
        productData.setCategory(brandPojo.getCategory());
        productData.setName(p.getName());
        productData.setId(p.getId());
        productData.setBarcode(p.getBarcode());
        productData.setMrp(p.getMrp());

        return productData;
    }

    protected static void normalize(ProductPojo p) {
        p.setName(StringUtil.toLowerCase(p.getName()));
    }

    protected void isOk(ProductForm form) throws ApiException
    {
        if(StringUtil.isEmpty(form.getName()))
            throw new ApiException("Product name cannot be empty");
        if(StringUtil.isEmpty(form.getBarcode()))
            throw new ApiException("barcode cannot be empty");
        if(StringUtil.isEmpty(form.getBrandName()))
            throw new ApiException("Brand name cannot be empty");
        if(StringUtil.isEmpty(form.getBrandCategory()))
            throw new ApiException("Category cannot be empty");
    }

}
