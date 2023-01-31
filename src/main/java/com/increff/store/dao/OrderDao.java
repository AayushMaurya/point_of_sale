package com.increff.store.dao;

import com.increff.store.pojo.OrderPojo;
import com.increff.store.api.ApiException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional
public class OrderDao {

    private static String SELECT_ALL = "select p from OrderPojo p";
    private static String SELECT_BY_ID = "select p from OrderPojo p where id=:id";
    private static String SELECT_BY_ORDERCODE = "select p from OrderPojo p where orderCode=:orderCode";
    private static String SELECT_BY_DATE_FILTER = "select p from OrderPojo p where placeDateTime>=:startDate and " +
            "placeDateTime<=:endDate";
    private static String SELECT_ALL_UNPLACED = "select p from OrderPojo p where status = 'pending'";
    private static String DELETE_BY_ID = "delete from OrderPojo p where id=:id";

    @PersistenceContext
    private EntityManager em;

    public Integer insert(OrderPojo pojo) {
        em.persist(pojo);
        em.flush();
        return pojo.getId();
    }

    public OrderPojo selectById(Integer id) {
        TypedQuery<OrderPojo> query = getQuery(SELECT_BY_ID);
        query.setParameter("id", id);
        return query.getResultStream().findFirst().orElse(null);

    }

    public List<OrderPojo> selectAll() {
        TypedQuery<OrderPojo> query = getQuery(SELECT_ALL);
        return query.getResultList();
    }

    public List<OrderPojo> selectByDateFilter(LocalDateTime startDate, LocalDateTime endDate) throws ApiException {
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

    public List<OrderPojo> selectAllUnplacedOrders() {
        TypedQuery<OrderPojo> query = getQuery(SELECT_ALL_UNPLACED);
        return query.getResultList();
    }

    public void delete(Integer id)
    {
        Query query = em.createQuery(DELETE_BY_ID);
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
