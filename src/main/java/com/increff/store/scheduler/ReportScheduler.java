package com.increff.store.scheduler;

import com.increff.store.dto.ReportDto;
import com.increff.store.service.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;

@EnableAsync
public class ReportScheduler {
    @Autowired
    ReportDto reportDto;

    @Async
    @Scheduled(cron = "${cron.expression}")
    public void createDailyReport() throws ApiException
    {
        reportDto.createDailyReport();
    }
}