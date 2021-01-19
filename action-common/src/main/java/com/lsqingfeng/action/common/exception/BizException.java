package com.lsqingfeng.action.common.exception;

import com.lsqingfeng.action.common.base.Result;

/**
 * @className: BizException
 * @description:
 * @author: sh.Liu
 * @date: 2021-01-19 16:47
 */
public class BizException extends RuntimeException {
    private Integer code;

    public BizException() {
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public BizException(Result.ResultCode resultCode) {
        super(resultCode.getMsg());
        this.code = resultCode.getCode();
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public BizException(Result.ResultCode resultCode, Throwable cause) {
        super(resultCode.getMsg(), cause);
        this.code = resultCode.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
