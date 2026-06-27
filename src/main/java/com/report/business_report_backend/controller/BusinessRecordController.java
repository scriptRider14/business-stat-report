package com.report.business_report_backend.controller;

import com.report.business_report_backend.common.Result;
import com.report.business_report_backend.dto.RecordQueryDTO;
import com.report.business_report_backend.dto.ReportQueryDTO;
import com.report.business_report_backend.entity.BusinessRecord;
import com.report.business_report_backend.entity.PageResult;
import com.report.business_report_backend.service.BusinessRecordService;
import com.report.business_report_backend.vo.CompareReportVO;
import com.report.business_report_backend.vo.DateReportVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/record")
@Tag(name = "收支记录报表接口", description = "明细分页、日期统计、同期环比分析")
public class BusinessRecordController {

    @Resource
    private BusinessRecordService businessRecordService;

    @PostMapping("/page")
    @Operation(summary = "分页查询（POST传参）", description = "多条件筛选收支明细分页")
    public Result<PageResult<BusinessRecord>> pagePost(@RequestBody RecordQueryDTO query) {
        PageResult<BusinessRecord> page = businessRecordService.queryPage(query);
        return Result.success(page);
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询（GET传参）", description = "部门、收支类型、时间区间筛选分页")
    public Result<PageResult<BusinessRecord>> pageGet(
            @RequestParam(required = false, defaultValue = "1") Long current,
            @RequestParam(required = false, defaultValue = "10") Long size,
            @RequestParam(required = false) String deptName,
            @RequestParam(required = false) Integer businessType,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime
    ) {
        RecordQueryDTO query = new RecordQueryDTO();
        query.setCurrent(current);
        query.setSize(size);
        query.setDeptName(deptName);
        query.setBusinessType(businessType);
        query.setStartTime(startTime);
        query.setEndTime(endTime);
        PageResult<BusinessRecord> page = businessRecordService.queryPage(query);
        return Result.success(page);
    }

    @PostMapping("/report/date")
    @Operation(summary = "收支日期统计 POST", description = "按日/按月汇总收支总额")
    public Result<List<DateReportVO>> reportPost(@RequestBody ReportQueryDTO query) {
        List<DateReportVO> list = businessRecordService.getDateReport(query);
        return Result.success(list);
    }

    @GetMapping("/report/date")
    @Operation(summary = "收支日期统计 GET", description = "groupType=day按天，month按月")
    public Result<List<DateReportVO>> reportGet(
            @RequestParam(required = false) String groupType,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime
    ) {
        ReportQueryDTO query = new ReportQueryDTO();
        query.setGroupType(groupType);
        query.setStartTime(startTime);
        query.setEndTime(endTime);
        List<DateReportVO> list = businessRecordService.getDateReport(query);
        return Result.success(list);
    }

    @GetMapping("/report/compare")
    @Operation(summary = "同期环比分析 GET", description = "自动计算上一周期收支环比增长率")
    public Result<CompareReportVO> compareGet(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime
    ) {
        ReportQueryDTO dto = new ReportQueryDTO();
        dto.setStartTime(startTime);
        dto.setEndTime(endTime);
        CompareReportVO vo = businessRecordService.getCompareReport(dto);
        return Result.success(vo);
    }

    @PostMapping("/report/compare")
    @Operation(summary = "同期环比分析 POST", description = "POST传参获取本期上期收支环比")
    public Result<CompareReportVO> comparePost(@RequestBody ReportQueryDTO query) {
        CompareReportVO vo = businessRecordService.getCompareReport(query);
        return Result.success(vo);
    }

}