package com.increff.store.dao;

import com.increff.store.pojo.ProductPojo;
import com.increff.store.service.ApiException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class ProductDao {

    private static String DELETE_BY_ID = "delete from ProductPojo p where id=:id";
    private static String SELECT_BY_ID = "select p from ProductPojo p where id=:id";
    private static String SELECT_ALL = "select p from ProductPojo p";
    private static String SELECT_BY_BARCODE = "select p from ProductPojo p where barcode=:id";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(ProductPojo p) {
        em.persist(p);
    }

    public ProductPojo selectById(int id) {
        TypedQuery<ProductPojo> query = getQuery(SELECT_BY_ID);
        query.setParameter("id", id);
        return query.getResultStream().findFirst().orElse(null);
    }

    public List<ProductPojo> selectAll() {
        TypedQuery<ProductPojo> query = getQuery(SELECT_ALL);
        return query.getResultList();
    }

    public ProductPojo selectByBarcode(String barcode) {
        TypedQuery<ProductPojo> query = getQuery(SELECT_BY_BARCODE);
        query.setParameter("id", barcode);
        return query.getResultStream().findFirst().orElse(null);
    }

    TypedQuery<ProductPojo> getQuery(String jpql) {
        return em.createQuery(jpql, ProductPojo.class);
    }

}
