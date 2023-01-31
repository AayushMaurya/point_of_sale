package com.increff.store.dto;

import com.increff.store.model.DailyReportData;
import com.increff.store.model.DateFilterForm;
import com.increff.store.pojo.DailyReportPojo;
import com.increff.store.pojo.OrderItemPojo;
import com.increff.store.pojo.OrderPojo;
import com.increff.store.api.ApiException;
import com.increff.store.api.OrderItemService;
import com.increff.store.api.OrderService;
import com.increff.store.api.ReportService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.increff.store.dto.DtoUtils.convertReportPojoToReportData;
import static com.increff.store.util.GetCurrentDataTime.getLocalDate;

@Service
public class ReportDto {
    @Autowired
    ReportService service;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderItemService orderItemService;

    private static Logger logger = Logger.getLogger(ReportDto.class);

    @Scheduled(cron = "${cron.expression}")
    public void createDailyReport() throws ApiException {
        logger.info("Creating daily report");
        DailyReportPojo reportPojo = new DailyReportPojo();

        LocalDate date = getLocalDate();

        Integer totalItems = 0;
        Double totalRevenue = 0.0;

        LocalDateTime startDate = null;
        LocalDateTime endDate = null;

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            startDate = LocalDate.parse(date.toString(), formatter).atStartOfDay();
            endDate = LocalDate.parse(date.toString(), formatter).atTime(23, 59, 59);
        }
        catch(Exception e)
        {
            logger.error(e);
        }
        List<OrderPojo> orderPojoList = orderService.selectOrderByDateFilter(startDate, endDate);

        Integer totalOrders = orderPojoList.size();

        for (OrderPojo o : orderPojoList) {
            Integer id = o.getId();
            List<OrderItemPojo> orderItemPojoList = orderItemService.getOrder(id);
            for (OrderItemPojo i : orderItemPojoList) {
                totalItems += i.getQuantity();
                totalRevenue += i.getQuantity() * i.getSellingPrice();
            }
        }

        reportPojo.setDate(date);
        reportPojo.setTotalRevenue(totalRevenue);
        reportPojo.setInvoicedItemsCount(totalItems);
        reportPojo.setInvoicedOrderCount(totalOrders);

        DailyReportPojo pojo = service.getReportByDate(date);
        if (pojo == null) {
            logger.info("Creating report for : " + date);
            service.addReport(reportPojo);
        } else {
            logger.info("Updating report");
            service.update(date, reportPojo);
        }
    }

    public List<DailyReportData> getAllDailyReport() throws ApiException
    {
        List<DailyReportPojo> dailyReportPojoList = service.getAllReport();
        List<DailyReportData> dailyReportData = new ArrayList<>();

        for (DailyReportPojo p : dailyReportPojoList) {
            dailyReportData.add(convertReportPojoToReportData(p));
        }

        return dailyReportData;
    }

    public List<DailyReportData> getAllDailyReport(DateFilterForm form) throws ApiException {
        LocalDate startDate;
        LocalDate endDate;
        try {
            startDate = LocalDate.parse(form.getStart(), DateTimeFormatter.ISO_DATE);
            endDate = LocalDate.parse(form.getEnd(), DateTimeFormatter.ISO_DATE);
        } catch (Exception e) {
            throw new ApiException("Please put valid start and end date");
        }
        List<DailyReportPojo> dailyReportPojoList = service.getAllReport(startDate, endDate);
        List<DailyReportData> dailyReportData = new ArrayList<>();

        for (DailyReportPojo p : dailyReportPojoList) {
            dailyReportData.add(convertReportPojoToReportData(p));
        }

        return dailyReportData;
    }

}
