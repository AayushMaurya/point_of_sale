package com.increff.store.dto;

import com.increff.store.model.DailyReportData;
import com.increff.store.model.DateFilterForm;
import com.increff.store.pojo.DailyReportPojo;
import com.increff.store.pojo.OrderItemPojo;
import com.increff.store.pojo.OrderPojo;
import com.increff.store.service.ApiException;
import com.increff.store.service.OrderItemService;
import com.increff.store.service.OrderService;
import com.increff.store.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void createDailyReport() throws ApiException {
        DailyReportPojo reportPojo = new DailyReportPojo();

        LocalDate date = getLocalDate();

        Integer totalItems = 0;
        Double totalRevenue = 0.0;

        String start = correctFormat(date.toString()) + " 00:00:00";
        String end = correctFormat(date.toString()) + " 23:59:59";

        LocalDateTime startDate = LocalDateTime.parse(start, DateTimeFormatter.ISO_DATE);
        LocalDateTime endDate = LocalDateTime.parse(end, DateTimeFormatter.ISO_DATE);

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
            service.addReport(reportPojo);
        } else {
            service.update(date, reportPojo);
        }
    }

    public List<DailyReportData> getAllDailyReport(DateFilterForm form) throws ApiException {
        LocalDate startDate;
        LocalDate endDate;
        try {
            startDate = LocalDate.parse(form.getStart(), DateTimeFormatter.ISO_DATE);
            endDate = LocalDate.parse(form.getEnd(), DateTimeFormatter.ISO_DATE);
        }
        catch(Exception e)
        {
            throw new ApiException("Please put valid start and end date");
        }
        List<DailyReportPojo> dailyReportPojoList = service.getAllReport(startDate, endDate);
        List<DailyReportData> dailyReportData = new ArrayList<>();

        for (DailyReportPojo p : dailyReportPojoList) {
            dailyReportData.add(convertReportPojoToReportData(p));
        }

        return dailyReportData;
    }

    String correctFormat(String date) {
        return date.replace('-', '/');
    }

}
