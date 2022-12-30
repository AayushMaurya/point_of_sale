package com.increff.store.dao;

import com.increff.store.pojo.BrandPojo;
import com.increff.store.service.ApiException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class BrandDao{
    private static String DELETE_ID = "delete from BrandPojo p where id=:id";
    private static String SELECT_ID = "select p from BrandPojo p where id=:id";
    private static String SELECT_ALL = "select p from BrandPojo p";
    private static String SELECT_BRAND_CATEGORY = "select p from BrandPojo p where brand=:id1 and category=:id2";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(BrandPojo p) {
        em.persist(p);
    }

    public void delete(int id) {
        Query query = em.createQuery(DELETE_ID);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    public BrandPojo select(int id) throws ApiException {
        try
        {
            TypedQuery<BrandPojo> query = getQuery(SELECT_ID);
            query.setParameter("id", id);
            return query.getSingleResult();
        }
        catch(Exception e)
        {
            throw new ApiException("No brand found with given id");
        }
    }

    public BrandPojo select_brand_category(String brand, String category){
        try
        {
            TypedQuery<BrandPojo> query = getQuery(SELECT_BRAND_CATEGORY);
            query.setParameter("id1", brand);
            query.setParameter("id2", category);
            return query.getSingleResult();
        }
        catch(Exception e)
        {
            return null;
        }
    }

    public List<BrandPojo> selectAll() {
        TypedQuery<BrandPojo> query = getQuery(SELECT_ALL);
        return query.getResultList();
    }

    public void update()
    {

    }

    TypedQuery<BrandPojo> getQuery(String jpql) {
        return em.createQuery(jpql, BrandPojo.class);
    }
}
