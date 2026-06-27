package com.report.business_report_backend.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BusinessRecord {

    private Long id;

    /** 部门名称 */
    private String deptName;

    /** 业务类型：1收入 2支出 */
    private Integer businessType;

    /** 金额 */
    private BigDecimal amount;

    /** 单据数量 */
    private Integer recordCount;

    /** 业务发生时间 */
    private LocalDateTime createTime;

    /** 备注 */
    private String remark;
}