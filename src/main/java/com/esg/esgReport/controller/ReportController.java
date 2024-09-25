package com.esg.esgReport.controller;

import com.esg.esgReport.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportController {
    @Autowired
    private ReportService reportService;

    @GetMapping("/generateReport")
    public ResponseEntity<byte[]> generateReport(@RequestParam String coId,
                                                 @RequestParam  Integer revNo,
                                                 @RequestParam String esgDiv){
        byte[] pdfBytes = reportService.generateReport(coId, revNo, esgDiv);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.inline().filename("EsgReport.pdf").build());


        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }
}