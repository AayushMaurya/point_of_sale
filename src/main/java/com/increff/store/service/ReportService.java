package com.increff.store.service;

import com.increff.store.dao.ReportDao;
import com.increff.store.pojo.DailyReportPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class ReportService {
    @Autowired
    private ReportDao dao;

    @Transactional
    public void addReport(DailyReportPojo pojo) throws ApiException {
        try {
            dao.insert(pojo);
        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }
    }

    @Transactional
    public List<DailyReportPojo> getAllReport() throws ApiException {
        try {
            return dao.selectAll();
        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }
    }

    @Transactional
    public DailyReportPojo getReportByDate(LocalDate date) throws ApiException {
        return dao.select(date);
    }

    @Transactional
    public void update(LocalDate date, DailyReportPojo newPojo)
    {
        DailyReportPojo pojo = dao.select(date);
        pojo.setInvoicedOrderCount(newPojo.getInvoicedOrderCount());
        pojo.setTotalRevenue(newPojo.getTotalRevenue());
        pojo.setInvoicedItemsCount(newPojo.getInvoicedItemsCount());
    }
}
