package com.increff.store.client;

import com.increff.store.api.ApiException;
import com.increff.store.dto.ReportDto;
import com.increff.store.model.form.InvoiceForm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.increff.store.flow.InvoiceGenerator;

@Service
public class InvoiceClient {
    @Autowired
    private InvoiceGenerator invoiceGenerator;
    private static Logger logger = Logger.getLogger(ReportDto.class);
    @Value("${fop.url}")
    private String fopUrl;
    private static String PDF_PATH = "./src/main/resources/pdf/";

    public void downloadInvoice(Integer orderId) throws ApiException {

        byte [] contents;
//        generating invoice form
        try {
            InvoiceForm invoiceForm = invoiceGenerator.generateInvoiceForOrder(orderId);
            RestTemplate restTemplate = new RestTemplate();
            contents = restTemplate.postForEntity(fopUrl, invoiceForm, byte[].class).getBody();
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ApiException("No able to connect to fop");
        }

        try {
//        saving pdf;
            Path pdfPath = Paths.get(PDF_PATH + orderId + "_invoice.pdf");
            Files.write(pdfPath, contents);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ApiException("Not able to create pdf at required path");
        }

        logger.info(orderId + "_invoice.pdf created");
    }
}
