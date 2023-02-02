package com.increff.store.dao;

import com.increff.store.pojo.ProductPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class ProductDao {
    private static String SELECT_BY_ID = "select p from ProductPojo p where id=:id";
    private static String SELECT_ALL = "select p from ProductPojo p";
    private static String SELECT_BY_BARCODE = "select p from ProductPojo p where barcode=:barcode";

    @PersistenceContext
    private EntityManager em;

    public Integer insert(ProductPojo p) {
        em.persist(p);
        em.flush();
        return p.getId();
    }

    public ProductPojo selectById(Integer id) {
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
        query.setParameter("barcode", barcode);
        return query.getResultStream().findFirst().orElse(null);
    }

    TypedQuery<ProductPojo> getQuery(String jpql) {
        return em.createQuery(jpql, ProductPojo.class);
    }

}
