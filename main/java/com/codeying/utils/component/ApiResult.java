package com.codeying.utils.component;

import java.io.Serializable;

/**
 * 接口返回信息封装对象
 *
 * @param <T> 实体参数类型
 *            200: 请求处理成功
 *            400: 请求处理失败
 *            500: 服务器内部错误
 *            401未认证（签名错误）
 *            404接口不存在
 */
public class ApiResult<T> implements Serializable {

    private Integer code;

    private Boolean success;

    private String message;

    private T data;

    public ApiResult() {
    }

    public ApiResult(Integer code, Boolean success) {
        this.code = code;
        this.success = success;
    }

    public ApiResult(Integer code, Boolean success, String message) {
        this.code = code;
        this.success = success;
        this.message = message;
    }

    public ApiResult(Integer code, Boolean success, String message, T data) {
        this.code = code;
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResult<T> success() {
        return new ApiResult<>(200, true, "成功");
    }

    public static <T> ApiResult<T> successMsg(String message) {
        return new ApiResult<>(200, true, message);
    }

    public static <T> ApiResult<T> successData(T data) {
        return new ApiResult<>(200, true, "成功", data);
    }

    public static <T> ApiResult<T> successMsgAndData(String message, T data) {
        return new ApiResult<>(200, true, message, data);
    }

    public static <T> ApiResult<T> fail() {
        return new ApiResult<>(500, false, "失败");
    }

    public static <T> ApiResult<T> fail(String message) {
        return new ApiResult<>(500, false, message, null);
    }

    public static <T> ApiResult<T> fail(String message, T data) {
        return new ApiResult<>(500, false, message, data);
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
