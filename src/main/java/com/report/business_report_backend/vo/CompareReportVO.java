package com.report.business_report_backend.vo;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CompareReportVO {
    // 本期
    private BigDecimal currentIncome;
    private BigDecimal currentExpend;
    private BigDecimal currentNet;

    // 上期
    private BigDecimal lastIncome;
    private BigDecimal lastExpend;
    private BigDecimal lastNet;

    // 环比增长率（百分比，保留两位小数）
    private String incomeRate;
    private String expendRate;
    private String netRate;
}