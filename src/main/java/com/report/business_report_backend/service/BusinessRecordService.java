package com.report.business_report_backend.service;

import com.report.business_report_backend.dto.RecordQueryDTO;
import com.report.business_report_backend.dto.ReportQueryDTO;
import com.report.business_report_backend.entity.BusinessRecord;
import com.report.business_report_backend.entity.PageResult;
import com.report.business_report_backend.vo.CompareReportVO;
import com.report.business_report_backend.vo.DateReportVO;

import java.util.List;

public interface BusinessRecordService {

    PageResult<BusinessRecord> queryPage(RecordQueryDTO query);

    List<DateReportVO> getDateReport(ReportQueryDTO query);
    CompareReportVO getCompareReport(ReportQueryDTO query);
}