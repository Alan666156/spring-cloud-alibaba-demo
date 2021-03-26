package com.fuhx.exception;

import com.fuhx.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 * @author fhx
 * @date 2021年3月13日
 */
@Slf4j
@RestControllerAdvice
//@ControllerAdvice
public class GlobalExceptionHandler {
        @ExceptionHandler(BusinessException.class)
        public Result<?> businessExceptionHandler(BusinessException businessException) {
                log.warn("get error:{}", businessException.getMessage());
                return Result.failure(businessException.getMessage());
        }

        @ExceptionHandler(Exception.class)
        public Result<?> systemExceptionHandler(Exception excepiton) {
                log.warn("get error:{}", excepiton.getMessage());
                return Result.failure("服务器忙，请稍后再试！");
        }
}