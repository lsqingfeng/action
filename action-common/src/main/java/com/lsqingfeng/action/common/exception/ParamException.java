package com.lsqingfeng.action.common.exception;

/**
 * @className: ParamException
 * @description:
 * @author: sh.Liu
 * @date: 2021-01-19 16:47
 */
public class ParamException extends RuntimeException {
    public ParamException() {
    }

    public ParamException(String message) {
        super(message);
    }

    public ParamException(String message, Throwable cause) {
        super(message, cause);
    }
}
