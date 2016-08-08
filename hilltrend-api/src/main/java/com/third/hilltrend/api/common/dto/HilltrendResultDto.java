package com.third.hilltrend.api.common.dto;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/7.
 */
public class HilltrendResultDto<T> implements Serializable {

    /**
     * 正确代码
     */
    public static final String SUCCESS_CODE = "0000";

    /**
     * 错误代码
     */
    public static final String ERROR_CODE = "1111";

    /**
     * 返回（正确/错误）代码
     */
    protected String code;

    /**
     * 返回信息描述
     */
    protected String msg;

    /**
     * 返回结果集
     */
    protected T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {

        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public HilltrendResultDto() {
        this.code = SUCCESS_CODE;
    }

    /**
     * 成功
     *
     * @param data
     */
    public HilltrendResultDto(T data) {
        this.code = SUCCESS_CODE;
        this.data = data;
    }

    /**
     * 成功
     *
     * @param msg
     * @param data
     */
    public HilltrendResultDto(String msg, T data) {
        this.code = SUCCESS_CODE;
        this.msg = msg;
        this.data = data;
    }


    /**
     * 成功or失败
     *
     * @param msg
     * @param isSuccess
     */
    public HilltrendResultDto(String msg, boolean isSuccess) {
        if (!isSuccess) {
            this.code = ERROR_CODE;
        }
        this.msg = msg;
    }

    /**
     * 成功or失败
     *
     * @param msg
     * @param isSuccess
     */
    public HilltrendResultDto(String msg, T data, boolean isSuccess) {
        if (!isSuccess) {
            this.code = ERROR_CODE;
        } else {
            this.code = SUCCESS_CODE;
        }
        this.msg = msg;
        this.data = data;
    }


    public HilltrendResultDto(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public HilltrendResultDto(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public boolean isSuccess() {
        return StringUtils.equalsIgnoreCase(SUCCESS_CODE, code);
    }


    public static HilltrendResultDto SUCCESS() {
        HilltrendResultDto resultDto = new HilltrendResultDto<>();
        resultDto.setCode(SUCCESS_CODE);
        return resultDto;
    }

    public static HilltrendResultDto SUCCESS(String message) {
        HilltrendResultDto resultDto = new HilltrendResultDto<>();
        resultDto.setCode(SUCCESS_CODE);
        resultDto.setMsg(message);
        return resultDto;
    }

    public static HilltrendResultDto FAIL(String message) {
        return new HilltrendResultDto<>(message, false);
    }
}
