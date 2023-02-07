package com.increff.store.scheduler;

import com.increff.store.api.ApiException;
import com.increff.store.dto.OrderDto;
import com.increff.store.dto.ReportDto;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

public class Scheduler {
    @Autowired
    private ReportDto reportDto;
    @Autowired
    private OrderDto orderDto;
    private static Logger logger = Logger.getLogger(ReportDto.class);

    @Async
    @Scheduled(cron = "${cron.expression}")
    public void createDailyReport() throws ApiException {
        logger.info("Creating Daily Report");
        reportDto.createDailyReport();
    }

    @Async
    @Scheduled(cron = "${cron.expression}")
    public void deleteUnplacedOrders() throws ApiException {
        logger.info("Deleting all unplaced orders");
        orderDto.deleteUnplacedOrders();
    }
}
