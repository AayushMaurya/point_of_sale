package com.increff.store.dao;

import com.increff.store.pojo.InventoryPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class InventoryDao {
    private static String SELECT_BY_ID = "select p from InventoryPojo p where id=:id";
    private static String SELECT_ALL = "select p from InventoryPojo p";

    @PersistenceContext
    private EntityManager em;

    public void insert(InventoryPojo p) {
        em.persist(p);
    }

    public InventoryPojo selectById(Integer id) {
        TypedQuery<InventoryPojo> query = getQuery(SELECT_BY_ID);
        query.setParameter("id", id);
        return query.getResultStream().findFirst().orElse(null);
    }

    public List<InventoryPojo> selectAll() {
        TypedQuery<InventoryPojo> query = getQuery(SELECT_ALL);
        return query.getResultList();
    }

    TypedQuery<InventoryPojo> getQuery(String jpql) {
        return em.createQuery(jpql, InventoryPojo.class);
    }

}
