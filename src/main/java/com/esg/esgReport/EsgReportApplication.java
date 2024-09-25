package com.esg.esgReport;

import net.sf.jasperreports.engine.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class EsgReportApplication {
	public static void main(String[] args) {
		SpringApplication.run(EsgReportApplication.class, args);
	}
}
