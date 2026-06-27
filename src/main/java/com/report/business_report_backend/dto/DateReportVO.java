package com.report.business_report_backend.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class DateReportVO {
    // 日期（日：2026-06-01  月：2026-06）
    private String dateStr;
    // 收入总金额
    private BigDecimal incomeAmount;
    // 支出总金额
    private BigDecimal expendAmount;
    // 单据总笔数
    private Long totalCount;
}