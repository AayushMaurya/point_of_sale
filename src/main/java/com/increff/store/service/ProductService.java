package com.increff.store.service;

import com.increff.store.dao.ProductDao;
import com.increff.store.pojo.BrandPojo;
import com.increff.store.pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductDao dao;

    @Autowired
    private BrandService brandService;

    @Autowired
    private InventoryService inventoryService;

    @Transactional
    public void add(ProductPojo p) throws ApiException
    {
        ProductPojo productPojo = get_barcode(p.getBarcode());
        if(productPojo!=null)
            throw new ApiException("Product with given barcode already exists");

        dao.insert(p);
    }

    @Transactional
    public void delete_id(int id)
    {
        dao.delete(id);
    }

    @Transactional
    public ProductPojo get(int id)
    {
        return dao.select(id);
    }

    @Transactional
    public List<ProductPojo> get_all()
    {
        return dao.selectAll();
    }

    @Transactional
    public void update(int id, ProductPojo newpojo)
    {
        ProductPojo p = dao.select(id);
        p.setBarcode(newpojo.getBarcode());
        p.setBrandCategory(newpojo.getBrandCategory());
        p.setName(newpojo.getName());
        p.setMrp(newpojo.getMrp());
    }

    @Transactional
    public ProductPojo get_barcode(String barcode) throws ApiException
    {
        return dao.select_code(barcode);
    }

}
