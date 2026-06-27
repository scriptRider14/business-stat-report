package com.report.business_report_backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.report.business_report_backend.mapper")
public class BusinessReportBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BusinessReportBackendApplication.class, args);
	}

}