package com.lsqingfeng.action.web.exception;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.google.common.base.Throwables;
import com.lsqingfeng.action.common.base.Result;
import com.lsqingfeng.action.common.exception.BizException;
import com.lsqingfeng.action.common.exception.ParamException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

/**
 * @className: GlobleExceptionHandler
 * @description:
 * @author: sh.Liu
 * @date: 2021-01-19 16:52
 */
@RestControllerAdvice
@Order(1)
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 统一参数异常处理
     *
     * @param e 统一参数异常
     * @return 通用返回
     */
    @ExceptionHandler(value = {ParamException.class, MethodArgumentNotValidException.class, ConstraintViolationException.class, BindException.class,
            HttpMessageNotReadableException.class, MissingServletRequestPartException.class, MissingServletRequestParameterException.class, MultipartException.class})
    public Result<?> paramsExceptionHandler(HttpServletRequest request, Exception e) {
        String msg;
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;
            msg = handlerErrors(ex.getBindingResult());
        } else if (e instanceof BindException) {
            BindException ex = (BindException) e;
            msg = handlerErrors(ex.getBindingResult());
        } else if (e instanceof ConstraintViolationException) {
            ConstraintViolationException ex = (ConstraintViolationException) e;
            Optional<ConstraintViolation<?>> first = ex.getConstraintViolations().stream().findFirst();
            msg = first.map(ConstraintViolation::getMessage).get();
        } else {
            msg = e.getMessage();
        }
        Result<?> result = Result.error(Result.ResultCode.PARAM_ERROR.getCode(), msg);
        return printLogAndReturn(request, result, e);
    }

    private String handlerErrors(BindingResult bindingResult) {
        List<FieldError> errors = bindingResult.getFieldErrors();
        FieldError error = errors.get(0);
        return error.getDefaultMessage();
    }

    /**
     * 自定义业务异常处理
     *
     * @param e 自定义业务异常
     * @return 通用返回
     */
    @ExceptionHandler(value = BizException.class)
    public Result<?> bizExceptionHandler(HttpServletRequest request, BizException e) {
        Result<?> result = Result.error(e.getCode() == null ? Result.ResultCode.BIZ_ERROR.getCode() : e.getCode()
                , e.getMessage());
        return printLogAndReturn(request, result, e);
    }

    /**
     * Http请求类型不支持异常处理
     *
     * @param e 自定义业务异常
     * @return 通用返回
     */
    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class, HttpMediaTypeNotSupportedException.class})
    public Result<?> httpRequestMethodNotSupportedExceptionHandler(HttpServletRequest request, Exception e) {
        Result<?> result = Result.error(Result.ResultCode.REQ_MODE_NOT_SUPPORTED);
        return printLogAndReturn(request, result, e);
    }

    /**
     * json解析异常
     *
     * @param e json异常
     * @return 通用返回
     */
    @ExceptionHandler(value = JSONException.class)
    public Result<?> jsonExceptionHandler(HttpServletRequest request, Exception e) {
        Result<?> result = Result.error(Result.ResultCode.JSON_FORMAT_ERROR);
        return printLogAndReturn(request, result, e);
    }

    /**
     * sql解析异常
     *
     * @param e 数据库sql异常
     * @return 通用返回
     */
    @ExceptionHandler(value = DataAccessException.class)
    public Result<?> sqlExceptionHandler(HttpServletRequest request, Exception e) {
        Result<?> result = Result.error(Result.ResultCode.SQL_ERROR);
        return printLogAndReturn(request, result, e);
    }

    /**
     * 普通异常捕获
     *
     * @param e 异常
     * @return 通用返回
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<?> exceptionHandler(HttpServletRequest request, Exception e) {
        Result<?> result = Result.error(Result.ResultCode.SYS_ERROR);
        return printLogAndReturn(request, result, e);
    }

    private Result<?> printLogAndReturn(HttpServletRequest request, Result<?> result, Exception e) {
        // 获取请求Url
        String requestUrl = request.getRequestURL().toString() + (StringUtils
                .isEmpty(request.getQueryString()) ? "" : "?" + request.getQueryString());
        log.error("<-异常返回-> 请求接口:{} | 异常时间:{} | 异常结果:{}", requestUrl, System.currentTimeMillis(), JSON.toJSONString(result));
        log.error("<--异常堆栈信息-->");
        log.error(Throwables.getStackTraceAsString(e));
        return result;
    }
}
