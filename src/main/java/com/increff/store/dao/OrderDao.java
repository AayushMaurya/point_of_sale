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
    private static String SELECT_ID = "select p from OrderPojo p where id=:id";
    private static String SELECT_DATE_FILTER = "select p from OrderPojo p where place_date_time>=:id1 and " +
            "place_date_time<=:id2";

    @PersistenceContext
    private EntityManager em;

    public void insert(OrderPojo p) throws ApiException
    {
        try {
            em.persist(p);
        }
        catch(Exception e)
        {
            System.out.println(e);
            throw new ApiException("Cannot insert the given order in order table");
        }
    }

    public OrderPojo get_id(int id) throws ApiException
    {
        try
        {
            TypedQuery<OrderPojo> query = getQuery(SELECT_ID);
            query.setParameter("id", id);
            return query.getSingleResult();
        }
        catch(Exception e)
        {
            throw new ApiException("No OrderPojo found with given id");
        }
    }

    public List<OrderPojo> select_all() throws ApiException
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

    public List<OrderPojo> select_date_filter(String start, String end) throws ApiException
    {
        try{
            TypedQuery<OrderPojo> query = getQuery(SELECT_DATE_FILTER);
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

}
