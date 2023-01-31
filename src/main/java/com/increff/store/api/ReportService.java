package com.increff.store.api;

import com.increff.store.dao.ReportDao;
import com.increff.store.pojo.DailyReportPojo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(rollbackOn = ApiException.class)
public class ReportService {
    @Autowired
    private ReportDao dao;

    private static Logger logger = Logger.getLogger(ReportService.class);

    public void addReport(DailyReportPojo pojo) throws ApiException {
        dao.insert(pojo);
    }

    public List<DailyReportPojo> getAllReport(LocalDate startDate, LocalDate endDate) throws ApiException {
        return dao.selectAll(startDate, endDate);
    }

    public List<DailyReportPojo> getAllReport() throws ApiException {
        return dao.selectAll();
    }

    public DailyReportPojo getReportByDate(LocalDate date) throws ApiException {
        return dao.select(date);
    }

    public void update(LocalDate date, DailyReportPojo newPojo) throws ApiException {
        DailyReportPojo pojo = dao.select(date);
        if (pojo == null) {
            logger.error("Report pojo to be updated is null");
            throw new ApiException("Cannot update daily report");
        }
        pojo.setInvoicedOrderCount(newPojo.getInvoicedOrderCount());
        pojo.setTotalRevenue(newPojo.getTotalRevenue());
        pojo.setInvoicedItemsCount(newPojo.getInvoicedItemsCount());
    }
}
