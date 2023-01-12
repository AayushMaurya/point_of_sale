package com.increff.store.dao;

import com.increff.store.pojo.BrandPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class BrandDao {
    private static String DELETE_BY_ID = "delete from BrandPojo p where id=:id";
    private static String SELECT_BY_ID = "select p from BrandPojo p where id=:id";
    private static String SELECT_ALL = "select p from BrandPojo p";
    private static String SELECT_BY_BRAND_CATEGORY = "select p from BrandPojo p where brand=:id1 and category=:id2";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(BrandPojo p) {
        em.persist(p);
    }

    public BrandPojo selectByBrandId(int id) {
        TypedQuery<BrandPojo> query = getQuery(SELECT_BY_ID);
        query.setParameter("id", id);
        return query.getResultStream()
                .findFirst()
                .orElse(null);
    }

    public BrandPojo selectByBrandCategory(String brand, String category) {
        TypedQuery<BrandPojo> query = getQuery(SELECT_BY_BRAND_CATEGORY);
        query.setParameter("id1", brand);
        query.setParameter("id2", category);
        return query.getResultStream()
                .findFirst()
                .orElse(null);
    }

    public List<BrandPojo> selectAll() {
        TypedQuery<BrandPojo> query = getQuery(SELECT_ALL);
        return query.getResultList();
    }

    TypedQuery<BrandPojo> getQuery(String jpql) {
        return em.createQuery(jpql, BrandPojo.class);
    }
}
