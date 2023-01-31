package com.increff.store.dao;

import com.increff.store.pojo.DailyReportPojo;
import com.increff.store.pojo.UserPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
public class ReportDao extends AbstractDao{
    private static String SELECT_BY_ID = "select p from DailyReportPojo p where date=:date";

    private static String SELECT_ALL = "select p from DailyReportPojo p";

    private static String SELECT_WITH_DATE_FILTER = "select p from DailyReportPojo p where date>=:startDate and " +
            "date<=:endDate";

    public void insert(DailyReportPojo p) {
        em().persist(p);
    }
    public DailyReportPojo select(LocalDate date) {
        TypedQuery<DailyReportPojo> query = getQuery(SELECT_BY_ID, DailyReportPojo.class);
        query.setParameter("date", date);
        return query.getResultStream().findFirst().orElse(null);
    }
    public List<DailyReportPojo> selectAll(LocalDate startDate, LocalDate endDate) {
        TypedQuery<DailyReportPojo> query = getQuery(SELECT_WITH_DATE_FILTER, DailyReportPojo.class);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }

    public List<DailyReportPojo> selectAll() {
        TypedQuery<DailyReportPojo> query = getQuery(SELECT_ALL, DailyReportPojo.class);
        return query.getResultList();
    }
}
