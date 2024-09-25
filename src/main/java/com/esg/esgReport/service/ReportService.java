package com.esg.esgReport.service;

import net.sf.jasperreports.engine.*;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;


@Service
public class ReportService {
    private static final String DB_URL = /* DB 커넥션 정보 */
    private static final String DB_USER = /* DB 커넥션 정보 */
    private static final String DB_PASSWORD = /* DB 커넥션 정보 */

//    private static final String[] REPORT_FILES = {
//            "Page01.jrxml", "Page02.jrxml", "Page03.jrxml", "Page04_05.jrxml",
//            "Page06.jrxml", "Page07_08.jrxml", "Page09_17.jrxml", "Page18_32.jrxml",
//            "Page33_35.jrxml"
//    };
//    private static final String[] REPORT_FILES = {
//            "Self_02.jrxml", "Self_03.jrxml", "Self_04.jrxml",
//            "Self_05.jrxml", "Self_06.jrxml", "Self_07.jrxml",
//            "Self_08.jrxml",
//    };

    private final ResourceLoader resourceLoader;

    // ResourceLoader를 생성자 주입받음
    public ReportService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    // 템플릿 경로를 동적으로 설정하는 메소드
    private String getTemplatePath() {
        Resource resource = resourceLoader.getResource("classpath:templates/");
        try {
            URL url = resource.getURL();
            String filePath = url.getPath();
            // URL에서 'file:' 접두사 제거
            return filePath.startsWith("file:") ? filePath.substring(5) : filePath;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

//    public void compileReports() throws JRException {
//        String templatePath = getTemplatePath();
//
//        for(String fileName : REPORT_FILES){
//            JasperCompileManager.compileReportToFile(templatePath + fileName);
//        }
//    }

    public byte[] generateReport(String coId, Integer revNo, String esgDiv){
        // 데이터베이스 연결
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)){
//            compileReports();
            
            // 리포트 경로
            String templatePath = getTemplatePath();
            
            // 리포트 컴파일
            JasperReport report = JasperCompileManager.compileReport(templatePath + "Page01.jrxml");
            System.out.println("Page01.jrxml compile completed!!");


            // 파라미터 설정
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("coId", coId);
            parameters.put("revNo", revNo);
            parameters.put("esgDiv", esgDiv);

            // 동적으로 이미지 경로 설정
            parameters.put("imagePath1", templatePath + "p1_1.png");
            parameters.put("imagePath2", templatePath + "p1_2.png");
            parameters.put("imagePath_p", templatePath + "p.png");
            parameters.put("imagePath_e", templatePath + "e.png");
            parameters.put("imagePath_s", templatePath + "s.png");
            parameters.put("imagePath_g", templatePath + "g.png");


            // 동적으로 서브리포트 경로 설정
            parameters.put("subreportPath2", templatePath + "Page02.jasper");
            parameters.put("subreportPath3", templatePath + "Page03.jasper");
            parameters.put("subreportPath4", templatePath + "Page04_05.jasper");
            parameters.put("subreportPath5", templatePath + "Page06.jasper");
            parameters.put("subreportPath6", templatePath + "Page07_08.jasper");
            parameters.put("subreportPath7", templatePath + "Page09_17.jasper");
            parameters.put("subreportPath8", templatePath + "Page18_32.jasper");
            parameters.put("subreportPath9", templatePath + "Page33_35.jasper");


            // 리포트 채우기
            JasperPrint print = JasperFillManager.fillReport(report, parameters, conn);

            // PDF로 출력
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(print, outputStream);

            // PDF 바이트 배열 변환
            return outputStream.toByteArray();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
