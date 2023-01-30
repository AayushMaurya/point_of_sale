package com.increff.store.client;

import com.increff.store.dto.ReportDto;
import com.increff.store.model.InvoiceForm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.increff.store.flow.InvoiceGenerator;

import javax.transaction.Transactional;

@Service
public class InvoiceClient {
    @Autowired
    private InvoiceGenerator invoiceGenerator;
    private static Logger logger = Logger.getLogger(ReportDto.class);
    @Value("${fop.url}")
    private String fopUrl;

    @Transactional(rollbackOn = Exception.class)
    public void downloadInvoice(Integer orderId) throws Exception {

//        generating invoice form
        InvoiceForm invoiceForm = invoiceGenerator.generateInvoiceForOrder(orderId);

        RestTemplate restTemplate = new RestTemplate();

        byte[] contents = restTemplate.postForEntity(fopUrl, invoiceForm, byte[].class).getBody();

//        saving pdf;
        Path pdfPath = Paths.get("./src/main/resources/pdf/" + orderId + "_invoice.pdf");

        Files.write(pdfPath, contents);

        logger.info(orderId + "_invoice.pdf created");
    }
}
