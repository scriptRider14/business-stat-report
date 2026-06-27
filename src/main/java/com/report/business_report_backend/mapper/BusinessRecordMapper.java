package com.report.business_report_backend.mapper;
import com.report.business_report_backend.vo.CompareReportVO;
import com.report.business_report_backend.dto.RecordQueryDTO;
import com.report.business_report_backend.dto.ReportQueryDTO;
import com.report.business_report_backend.entity.BusinessRecord;
import com.report.business_report_backend.vo.DateReportVO;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface BusinessRecordMapper {

    // 分页查询列表
    List<BusinessRecord> selectPageList(
            @Param("query") RecordQueryDTO query,
            @Param("offset") Long offset,
            @Param("pageSize") Long pageSize
    );
    CompareReportVO sumAmountByTimeRange(
            @Param("start") String start,
            @Param("end") String end
    );

    // 查询总条数
    Long selectCount(@Param("query") RecordQueryDTO query);

    // 日期汇总统计
    List<DateReportVO> selectDateReport(@Param("query") ReportQueryDTO query);
}