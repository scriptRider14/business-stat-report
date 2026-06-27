package com.report.business_report_backend.common;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 业务自定义异常
    @ExceptionHandler(RuntimeException.class)
    public Result<?> runtimeException(RuntimeException e) {
        return Result.fail(500, e.getMessage());
    }

    // 全局兜底异常
    @ExceptionHandler(Exception.class)
    public Result<?> globalException(Exception e) {
        e.printStackTrace();
        return Result.fail(500, "系统内部异常，请稍后重试");
    }
}