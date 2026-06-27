package com.report.business_report_backend.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RecordQueryDTO {
    // 当前页码
    private Long current = 1L;
    // 每页条数
    private Long size = 10L;
    // 部门名称模糊查询
    private String deptName;
    // 业务类型 1收入 2支出
    private Integer businessType;
    // 开始时间
    private LocalDateTime startTime;
    // 结束时间
    private LocalDateTime endTime;
}