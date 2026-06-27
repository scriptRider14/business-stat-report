package com.report.business_report_backend.service.impl;

import com.report.business_report_backend.dto.RecordQueryDTO;
import com.report.business_report_backend.dto.ReportQueryDTO;
import com.report.business_report_backend.entity.BusinessRecord;
import com.report.business_report_backend.entity.PageResult;
import com.report.business_report_backend.mapper.BusinessRecordMapper;
import com.report.business_report_backend.service.BusinessRecordService;
import com.report.business_report_backend.vo.CompareReportVO;
import com.report.business_report_backend.vo.DateReportVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class BusinessRecordServiceImpl implements BusinessRecordService {

    @Resource
    private BusinessRecordMapper businessRecordMapper;

    @Override
    public PageResult<BusinessRecord> queryPage(RecordQueryDTO query) {
        // Java层计算分页偏移量，避免MyBatis表达式报错
        Long offset = (query.getCurrent() - 1) * query.getSize();
        List<BusinessRecord> list = businessRecordMapper.selectPageList(query, offset, query.getSize());
        Long total = businessRecordMapper.selectCount(query);
        return PageResult.build(list, total, query.getCurrent(), query.getSize());
    }

    @Override
    public List<DateReportVO> getDateReport(ReportQueryDTO query) {
        return businessRecordMapper.selectDateReport(query);
    }

    @Override
    public CompareReportVO getCompareReport(ReportQueryDTO query) {
        LocalDateTime startTime = query.getStartTime();
        LocalDateTime endTime = query.getEndTime();
        if (startTime == null || endTime == null) {
            throw new RuntimeException("起止时间不能为空");
        }
        long days = ChronoUnit.DAYS.between(startTime, endTime) + 1;

        // 本期时间区间
        String currStart = startTime.toString().replace("T", " ");
        String currEnd = endTime.toString().replace("T", " ");

        // 上期整体平移相同天数
        LocalDateTime lastStart = startTime.minusDays(days);
        LocalDateTime lastEnd = endTime.minusDays(days);
        String lastStartStr = lastStart.toString().replace("T", " ");
        String lastEndStr = lastEnd.toString().replace("T", " ");

        CompareReportVO curr = businessRecordMapper.sumAmountByTimeRange(currStart, currEnd);
        CompareReportVO last = businessRecordMapper.sumAmountByTimeRange(lastStartStr, lastEndStr);

        // 核心修复：如果查询结果为null，手动new空VO
        if (curr == null) {
            curr = new CompareReportVO();
        }
        if (last == null) {
            last = new CompareReportVO();
        }

        // 防止字段为null，统一转为0
        BigDecimal currIncome = curr.getCurrentIncome() == null ? BigDecimal.ZERO : curr.getCurrentIncome();
        BigDecimal currExpend = curr.getCurrentExpend() == null ? BigDecimal.ZERO : curr.getCurrentExpend();
        BigDecimal lastIncome = last.getCurrentIncome() == null ? BigDecimal.ZERO : last.getCurrentIncome();
        // 修正拼写错误 getCurrrentExpend → getCurrentExpend
        BigDecimal lastExpend = last.getCurrentExpend() == null ? BigDecimal.ZERO : last.getCurrentExpend();

        BigDecimal currNet = currIncome.subtract(currExpend);
        BigDecimal lastNet = lastIncome.subtract(lastExpend);

        CompareReportVO result = new CompareReportVO();
        result.setCurrentIncome(currIncome);
        result.setCurrentExpend(currExpend);
        result.setCurrentNet(currNet);
        result.setLastIncome(lastIncome);
        result.setLastExpend(lastExpend);
        result.setLastNet(lastNet);

        // 计算环比增长率
        result.setIncomeRate(calcRate(currIncome, lastIncome));
        result.setExpendRate(calcRate(currExpend, lastExpend));
        result.setNetRate(calcRate(currNet, lastNet));

        return result;
    }

    /**
     * 计算环比百分比
     * @param curr 本期值
     * @param last 上期值
     * @return 百分比字符串
     */
    private String calcRate(BigDecimal curr, BigDecimal last) {
        if (last.compareTo(BigDecimal.ZERO) == 0) {
            return "-";
        }
        BigDecimal diff = curr.subtract(last);
        BigDecimal rate = diff.divide(last, 4, RoundingMode.HALF_UP)
                .multiply(new BigDecimal("100"));
        return rate.setScale(2, RoundingMode.HALF_UP) + "%";
    }

}