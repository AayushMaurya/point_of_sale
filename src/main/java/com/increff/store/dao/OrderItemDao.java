package com.increff.store.dao;

import com.increff.store.pojo.OrderItemPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class OrderItemDao {
    private static String SELECT_BY_ID = "select p from OrderItemPojo p where id=:id";
    private static String SELECT_BY_PRODUCTID_ORDERID = "select p from OrderItemPojo p where productId=:id1 and " +
            "orderId=:id2";
    private static String SELECT_BY_ORDER_ID = "select p from OrderItemPojo p where orderId=:id";
    private static String DELETE_ITEM_BY_ID = "delete from OrderItemPojo p where id=:id";
    private static String SELECT_ALL = "select p from OrderItemPojo p";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Integer insert(OrderItemPojo p) {
        em.persist(p);
        em.flush();
        return p.getId();
    }

    @Transactional
    public OrderItemPojo selectByItemId(Integer id) {
        TypedQuery<OrderItemPojo> query = getQuery(SELECT_BY_ID);
        query.setParameter("id", id);
        return query.getResultStream().findFirst().orElse(null);

    }

    public List<OrderItemPojo> selectById(Integer orderId) {
        TypedQuery<OrderItemPojo> query = getQuery(SELECT_BY_ORDER_ID);
        query.setParameter("id", orderId);
        return query.getResultList();
    }

    public void deleteById(Integer id) {
        Query query = em.createQuery(DELETE_ITEM_BY_ID);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    public List<OrderItemPojo> selectAll() {
        TypedQuery<OrderItemPojo> query = getQuery(SELECT_ALL);
        return query.getResultList();
    }

    TypedQuery<OrderItemPojo> getQuery(String jpql) {
        return em.createQuery(jpql, OrderItemPojo.class);
    }

    public OrderItemPojo selectByProductIdOrderId(Integer productId, Integer orderId) {

        TypedQuery<OrderItemPojo> query = getQuery(SELECT_BY_PRODUCTID_ORDERID);
        query.setParameter("id1", productId);
        query.setParameter("id2", orderId);
        return query.getResultStream().findFirst().orElse(null);

    }
}
