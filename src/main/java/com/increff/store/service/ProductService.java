package com.increff.store.service;

import com.increff.store.dao.ProductDao;
import com.increff.store.pojo.ProductPojo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional(rollbackOn = ApiException.class)
public class ProductService {

    @Autowired
    private ProductDao dao;

    private static Logger logger = Logger.getLogger(ProductService.class);

    public void addProduct(ProductPojo pojo) throws ApiException {
        ProductPojo productPojo = dao.selectByBarcode(pojo.getBarcode());
        if (productPojo != null)
            throw new ApiException("Product with given barcode already exists");

        dao.insert(pojo);
    }

    public ProductPojo getProductById(Integer id) throws ApiException {
        ProductPojo productPojo = dao.selectById(id);
        if (productPojo == null)
            throw new ApiException("Cannot select a product with given product id");
        return productPojo;
    }

    public List<ProductPojo> getAllProducts() {
        return dao.selectAll();
    }

    public void updateProduct(Integer id, ProductPojo newPojo) throws ApiException {
        ProductPojo p = dao.selectById(id);
        if(p==null)
        {
            logger.error("Product pojo to be updated is null");
            throw new ApiException("Cannot update the product");
        }
        p.setBarcode(newPojo.getBarcode());
        p.setBrandCategory(newPojo.getBrandCategory());
        p.setName(newPojo.getName());
        p.setMrp(newPojo.getMrp());
    }

    public ProductPojo getProductByBarcode(String barcode) throws ApiException {
        ProductPojo productPojo = dao.selectByBarcode(barcode);
        if (productPojo == null)
            throw new ApiException("Cannot find product with given bar code");
        return productPojo;
    }
}
