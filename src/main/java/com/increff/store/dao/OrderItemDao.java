package com.increff.store.dao;

import com.increff.store.pojo.OrderItemPojo;
import com.increff.store.service.ApiException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class OrderItemDao {
    private static String SELECT_ID = "select p from OrderItemPojo p where id=:id";
    private static String SELECT_ORDER_ID = "select p from OrderItemPojo p where orderid=:id";
    private static String DELETE_ITEM_ID = "delete from OrderItemPojo p where id=:id";
    private static String SELECT_ALL = "select p from OrderItemPojo p";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(OrderItemPojo p) {
        em.persist(p);
    }

    @Transactional
    public OrderItemPojo select_ItemId(int id) throws ApiException
    {
        try{
            TypedQuery<OrderItemPojo> query = getQuery(SELECT_ID);
            query.setParameter("id", id);
            return query.getSingleResult();
        }
        catch(Exception e)
        {
            System.out.println(e);
            throw new ApiException("No order found with given id");
        }
    }

    public List<OrderItemPojo> select_order(int orderId)
    {
        TypedQuery<OrderItemPojo> query = getQuery(SELECT_ORDER_ID);
        query.setParameter("id", orderId);
        return query.getResultList();
    }

    public void delete_ItemId(int id)
    {
        Query query = em.createQuery(DELETE_ITEM_ID);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    public List<OrderItemPojo> get_all()
    {
        TypedQuery<OrderItemPojo> query = getQuery(SELECT_ALL);
        return query.getResultList();
    }

    TypedQuery<OrderItemPojo> getQuery(String jpql) {
        return em.createQuery(jpql, OrderItemPojo.class);
    }

}
