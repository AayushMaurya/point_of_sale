package com.increff.store.scheduler;

import com.increff.store.dto.ReportDto;
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
    public void createDailyReport()
    {
        System.out.println("creating report");
        try {
            reportDto.createDailyReport();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
