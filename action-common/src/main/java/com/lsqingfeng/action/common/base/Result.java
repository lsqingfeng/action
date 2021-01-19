package com.lsqingfeng.action.common.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @className: Result
 * @description:
 * @author: sh.Liu
 * @date: 2021-01-19 16:39
 */
@Data
@ApiModel(description = "统一返回对象")
public class Result<T> implements Serializable {

    private static final long serialVersionUID = -3960261604605958516L;

    @ApiModelProperty(value = "状态码")
    private int code;
    @ApiModelProperty(value = "返回信息")
    private String msg;
    @ApiModelProperty(value = "返回数据")
    private T data;

    /**
     * 成功,默认状态码,返回消息,无返回数据
     *
     * @param <T> 返回类泛型
     * @return 通用返回Result
     */
    public static <T> Result<T> success() {
        return new Result<>();
    }

    /**
     * 成功,默认状态码,返回消息,自定义返回数据
     *
     * @param data 自定义返回数据
     * @param <T>  返回类泛型,不能为String
     * @return 通用返回Result
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(data);
    }

    /**
     * 成功,默认状态码,自定义返回消息,无返回数据
     *
     * @param msg 自定义返回消息
     * @param <T> 返回类泛型
     * @return 通用返回Result
     */
    public static <T> Result<T> success(String msg) {
        return new Result<>(msg);
    }

    /**
     * 成功,默认状态码,自定义返回消息,返回数据
     *
     * @param msg  自定义返回消息
     * @param data 自定义返回数据
     * @param <T>  返回类泛型
     * @return 通用返回Result
     */
    public static <T> Result<T> success(String msg, T data) {
        return new Result<>(msg, data);
    }

    /**
     * 失败,默认状态码,返回消息,无返回数据
     *
     * @param <T> 返回类泛型
     * @return 通用返回Result
     */
    public static <T> Result<T> error() {
        return new Result<>(ResultCode.ERROR);
    }

    /**
     * 失败,默认状态码,自定义返回消息,无返回数据
     *
     * @param <T> 返回类泛型
     * @return 通用返回Result
     */
    public static <T> Result<T> error(String msg) {
        return new Result<>(ResultCode.ERROR.getCode(), msg);
    }

    /**
     * 失败,自定义状态码,返回消息,无返回数据
     *
     * @param code 自定义状态码
     * @param msg  自定义返回消息
     * @param <T>  返回类泛型
     * @return 通用返回Result
     */
    public static <T> Result<T> error(int code, String msg) {
        return new Result<>(code, msg);
    }

    /**
     * 失败,使用CodeMsg状态码,返回消息,无返回数据
     *
     * @param resultCode CodeMsg,参数如下:
     *                   <p> code 状态码
     *                   <p> msg  返回消息
     * @param <T>        返回类泛型
     * @return 通用返回Result
     */
    public static <T> Result<T> error(ResultCode resultCode) {
        return new Result<>(resultCode);
    }

    /**
     * 成功构造器,无返回数据
     */
    private Result() {
        this(ResultCode.SUCCESS);
    }

    /**
     * 成功构造器,自定义返回数据
     *
     * @param data 返回数据
     */
    private Result(T data) {
        this(ResultCode.SUCCESS, data);
    }

    /**
     * 成功构造器,自定义返回消息,无返回数据
     *
     * @param msg 返回消息
     */
    private Result(String msg) {
        this(ResultCode.SUCCESS.getCode(), msg);
    }

    /**
     * 成功构造器,自定义返回信息,返回数据
     *
     * @param msg  返回信息
     * @param data 返回数据
     */
    private Result(String msg, T data) {
        this(ResultCode.SUCCESS.getCode(), msg, data);
    }

    /**
     * 构造器,自定义状态码,返回消息
     *
     * @param code 状态码
     * @param msg  返回消息
     */
    private Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 构造器,自定义状态码,返回消息,返回数据
     *
     * @param code 状态码
     * @param msg  返回消息
     * @param data 返回数据
     */
    private Result(int code, String msg, T data) {
        this(code, msg);
        this.data = data;
    }

    /**
     * 构造器,使用CodeMsg状态码与返回信息
     *
     * @param resultCode CodeMsg,参数如下:
     *                   <p> code 状态码
     *                   <p> msg  返回消息
     */
    private Result(ResultCode resultCode) {
        this(resultCode.getCode(), resultCode.getMsg());
    }

    /**
     * 构造器,使用CodeMsg状态码与返回信息,自定义返回数据
     *
     * @param resultCode CodeMsg,参数如下:
     *                   <p> code 状态码
     *                   <p> msg  返回消息
     * @param data       返回数据
     */
    private Result(ResultCode resultCode, T data) {
        this(resultCode);
        this.data = data;
    }

    @Data
    @ApiModel(description = "统一分页列表信息返回对象")
    public static class ResultPage<T> implements Serializable {

        /**
         * 当前页
         */
        @ApiModelProperty(value = "当前页")
        Long currPage;
        /**
         * 每页条数
         */
        @ApiModelProperty(value = "每页条数")
        Long pageSize;
        /**
         * 总页数
         */
        @ApiModelProperty(value = "总页数")
        Long pages;
        /**
         * 总条数
         */
        @ApiModelProperty(value = "总条数")
        Long total;
        /**
         * 是否有下一页
         */
        @ApiModelProperty(value = "是否有下一页")
        Boolean hasNext;
        /**
         * 数据集合
         */
        @ApiModelProperty(value = "数据列表数据")
        List<T> list;

//        /**
//         * 获取mybatis plus分页对象
//         *
//         * @param page mybatis plus分页对象
//         * @param <T>  泛型类型
//         * @return 返回分页对象
//         */
//        public static <T> ResultPage<T> build(IPage<T> page) {
//            ResultPage<T> resultPage = new ResultPage<>();
//            resultPage.setList(page.getRecords());
//            resultPage.setCurrPage(page.getCurrent());
//            resultPage.setPageSize(page.getSize());
//            resultPage.setPages(page.getPages());
//            resultPage.setTotal(page.getTotal());
//            resultPage.setHasNext(page.getCurrent() < page.getPages());
//            return resultPage;
//        }
//
//        /**
//         * 获取page helper分页对象
//         *
//         * @param pageInfo page helper分页对象
//         * @param <T>      泛型类型
//         * @return 返回分页对象
//         */
//        public static <T> ResultPage<T> build(PageInfo<T> pageInfo) {
//            ResultPage<T> resultPage = new ResultPage<>();
//            resultPage.setList(pageInfo.getList());
//            resultPage.setCurrPage((long) pageInfo.getPageNum());
//            resultPage.setPageSize((long) pageInfo.getPageSize());
//            resultPage.setPages((long) pageInfo.getPages());
//            resultPage.setTotal(pageInfo.getTotal());
//            resultPage.setHasNext(pageInfo.isHasNextPage());
//            return resultPage;
//        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResultCode {
        /**
         * 状态码
         */
        private int code;

        /**
         * 状态信息
         */
        private String msg;

        /**
         * 默认成功
         */
        public final static ResultCode SUCCESS = ResultCode.dispose(ResultCodeEnum.SUCCESS);
        /**
         * 默认失败
         */
        public final static ResultCode ERROR = ResultCode.dispose(ResultCodeEnum.ERROR);
        /**
         * 通用业务异常
         */
        public final static ResultCode BIZ_ERROR = ResultCode.dispose(ResultCodeEnum.BIZ_ERROR);
        /**
         * 文件超出最大限制
         */
        public final static ResultCode FILE_OUT_MAX = ResultCode.dispose(ResultCodeEnum.FILE_OUT_MAX);
        /**
         * 文件格式不正确
         */
        public final static ResultCode FILE_FORMAT_ERROR = ResultCode.dispose(ResultCodeEnum.FILE_FORMAT_ERROR);
        /**
         * 参数错误
         */
        public final static ResultCode PARAM_ERROR = ResultCode.dispose(ResultCodeEnum.PARAM_ERROR);
        /**
         * Json解析异常
         */
        public final static ResultCode JSON_FORMAT_ERROR = ResultCode.dispose(ResultCodeEnum.JSON_FORMAT_ERROR);
        /**
         * Sql解析异常
         */
        public final static ResultCode SQL_ERROR = ResultCode.dispose(ResultCodeEnum.SQL_ERROR);
        /**
         * 网络超时
         */
        public final static ResultCode NETWORK_TIMEOUT = ResultCode.dispose(ResultCodeEnum.NETWORK_TIMEOUT);
        /**
         * 未知的接口
         */
        public final static ResultCode UNKNOWN_INTERFACE = ResultCode.dispose(ResultCodeEnum.UNKNOWN_INTERFACE);
        /**
         * 请求方式不支持
         */
        public final static ResultCode REQ_MODE_NOT_SUPPORTED = ResultCode.dispose(ResultCodeEnum.REQ_MODE_NOT_SUPPORTED);
        /**
         * 系统异常
         */
        public final static ResultCode SYS_ERROR = ResultCode.dispose(ResultCodeEnum.SYS_ERROR);

        private static ResultCode dispose(ResultCodeEnum codeEnum) {
            return ResultCode.builder().code(codeEnum.getCode()).msg(codeEnum.getMsg()).build();
        }
    }

    enum ResultCodeEnum {
        SUCCESS(0, "操作成功"),
        ERROR(1, "操作失败"),
        BIZ_ERROR(1000, "通用业务异常"),
        FILE_OUT_MAX(9000, "文件超出最大限制"),
        FILE_FORMAT_ERROR(9001, "文件格式不正确"),
        PARAM_ERROR(9050, "参数错误"),
        JSON_FORMAT_ERROR(9051, "Json解析异常"),
        SQL_ERROR(9052, "Sql解析异常"),
        NETWORK_TIMEOUT(9510, "网络超时"),
        UNKNOWN_INTERFACE(9520, "未知的接口"),
        REQ_MODE_NOT_SUPPORTED(9530, "请求方式不支持"),
        SYS_ERROR(9999, "系统异常");

        /**
         * 状态码
         */
        private int code;

        /**
         * 状态信息
         */
        private String msg;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        ResultCodeEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }
}
