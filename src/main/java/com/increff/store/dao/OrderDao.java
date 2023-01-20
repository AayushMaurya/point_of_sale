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
    private static String SELECT_BY_ORDERCODE = "select p from OrderPojo p where orderCode=:orderCode";
    private static String SELECT_BY_DATE_FILTER = "select p from OrderPojo p where placeDateTime>=:startDate and " +
            "placeDateTime<=:endDate";

    @PersistenceContext
    private EntityManager em;

    public Integer insert(OrderPojo pojo) throws ApiException {
        em.persist(pojo);
        em.flush();
        return pojo.getId();
    }

    public OrderPojo selectById(Integer id) {
        TypedQuery<OrderPojo> query = getQuery(SELECT_BY_ID);
        query.setParameter("id", id);
        return query.getResultStream().findFirst().orElse(null);

    }

    public List<OrderPojo> selectAll() throws ApiException {
        try {
            TypedQuery<OrderPojo> query = getQuery(SELECT_ALL);
            return query.getResultList();
        } catch (Exception e) {
            throw new ApiException("cannot select the orders from order table");
        }
    }

    public List<OrderPojo> selectByDateFilter(String startDate, String endDate) throws ApiException {
        TypedQuery<OrderPojo> query = getQuery(SELECT_BY_DATE_FILTER);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }

    TypedQuery<OrderPojo> getQuery(String jpql) {
        return em.createQuery(jpql, OrderPojo.class);
    }

    public OrderPojo selectByOrderCode(String orderCode) {
        TypedQuery<OrderPojo> query = getQuery(SELECT_BY_ORDERCODE);
        query.setParameter("orderCode", orderCode);
        return query.getResultStream().findFirst().orElse(null);
    }
}
