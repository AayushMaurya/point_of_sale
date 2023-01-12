package com.increff.store.service;

import com.increff.store.dao.ProductDao;
import com.increff.store.pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductDao dao;

    @Transactional
    public void addProduct(ProductPojo p) throws ApiException {
        ProductPojo productPojo = dao.selectByBarcode(p.getBarcode());
        if (productPojo != null)
            throw new ApiException("Product with given barcode already exists");

        dao.insert(p);
    }

    @Transactional
    public ProductPojo getProductById(int id) throws ApiException {
        ProductPojo productPojo = dao.selectById(id);
        if (productPojo == null)
            throw new ApiException("Cannot select a product with given product id");
        return productPojo;
    }

    @Transactional
    public List<ProductPojo> getAllProducts() {
        return dao.selectAll();
    }

    @Transactional
    public void update(int id, ProductPojo newpojo) {
        ProductPojo p = dao.selectById(id);
        p.setBarcode(newpojo.getBarcode());
        p.setBrandCategory(newpojo.getBrandCategory());
        p.setName(newpojo.getName());
        p.setMrp(newpojo.getMrp());
    }

    @Transactional
    public ProductPojo getProductByBarcode(String barcode) throws ApiException {
        ProductPojo productPojo = dao.selectByBarcode(barcode);
        if (productPojo == null)
            throw new ApiException("Cannot find product with given bar code");
        return productPojo;
    }

}
