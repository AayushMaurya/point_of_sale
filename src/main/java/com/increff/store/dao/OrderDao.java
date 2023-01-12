package com.increff.store.dao;

import com.increff.store.pojo.OrderPojo;
import com.increff.store.service.ApiException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class OrderDao {

    private static String SELECT_ALL = "select p from OrderPojo p";
    private static String SELECT_BY_ID = "select p from OrderPojo p where id=:id";
    private static String SELECT_BY_ORDERCODE = "select p from OrderPojo p where orderCode=:id";
    private static String SELECT_BY_DATE_FILTER = "select p from OrderPojo p where placeDateTime>=:id1 and " +
            "placeDateTime<=:id2";

    @PersistenceContext
    private EntityManager em;

    public Integer insert(OrderPojo p) throws ApiException
    {
        try {
            em.persist(p);
            em.flush();
            return p.getId();
        }
        catch(Exception e)
        {
            System.out.println(e);
            throw new ApiException("Cannot insert the given order in order table");
        }
    }

    public OrderPojo selectById(int id){
            TypedQuery<OrderPojo> query = getQuery(SELECT_BY_ID);
            query.setParameter("id", id);
            return query.getResultStream().findFirst().orElse(null);

    }

    public List<OrderPojo> selectAll() throws ApiException
    {
        try{
            TypedQuery<OrderPojo> query = getQuery(SELECT_ALL);
            return query.getResultList();
        }
        catch(Exception e)
        {
            System.out.println(e);
            throw new ApiException("cannot select the orders from order table");
        }
    }

    public List<OrderPojo> selectByDateFilter(String start, String end) throws ApiException
    {
        try{
            TypedQuery<OrderPojo> query = getQuery(SELECT_BY_DATE_FILTER);
            query.setParameter("id1", start);
            query.setParameter("id2", end);
            return query.getResultList();
        }
        catch(Exception e)
        {
            System.out.println(e);
            throw new ApiException("cannot select the orders from order table with given dates");
        }
    }
    TypedQuery<OrderPojo> getQuery(String jpql) {
        return em.createQuery(jpql, OrderPojo.class);
    }

    public OrderPojo selectByOrderCode(String orderCode){
        TypedQuery<OrderPojo> query = getQuery(SELECT_BY_ORDERCODE);
        query.setParameter("id", orderCode);
        return query.getResultStream().findFirst().orElse(null);

    }
}
