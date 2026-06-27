package com.report.business_report_backend.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReportQueryDTO {
    // 汇总类型 day=按日  month=按月
    private String groupType;
    // 开始时间
    private LocalDateTime startTime;
    // 结束时间
    private LocalDateTime endTime;
}