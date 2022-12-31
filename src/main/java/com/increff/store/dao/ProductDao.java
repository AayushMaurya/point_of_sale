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

    private static String DELETE_ID = "delete from ProductPojo p where id=:id";
    private static String SELECT_ID = "select p from ProductPojo p where id=:id";
    private static String SELECT_ALL = "select p from ProductPojo p";
    private static String SELECT_CODE = "select p from ProductPojo p where barcode=:id";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(ProductPojo p) {
        em.persist(p);
    }

    public void delete(int id) {
        Query query = em.createQuery(DELETE_ID);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    public ProductPojo select(int id) {
        TypedQuery<ProductPojo> query = getQuery(SELECT_ID);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    public List<ProductPojo> selectAll() {
        TypedQuery<ProductPojo> query = getQuery(SELECT_ALL);
        return query.getResultList();
    }

    public ProductPojo select_code(String barcode)
    {
        try{
            TypedQuery<ProductPojo> query = getQuery(SELECT_CODE);
            query.setParameter("id", barcode);
            return query.getSingleResult();
        }
        catch(Exception e)
        {
            System.out.println(e);
            return null;
        }
    }

    TypedQuery<ProductPojo> getQuery(String jpql) {
        return em.createQuery(jpql, ProductPojo.class);
    }

}
