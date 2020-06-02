package com.paas.service.common;

import lombok.Data;

/**
 * create by sumerian on 2020/6/2
 * <p>
 * desc:
 **/
@Data
public class PaasResponnse<T> {

    public PaasResponnse(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public PaasResponnse(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public PaasResponnse() {
    }

    private String code;
    private String msg;
    private T data;

    public static <T> PaasResponnse<T> success(T data) {
        return new PaasResponnse<>(ExceptionCode.SUCCESS.getCode(), ExceptionCode.SUCCESS.getName(), data);
    }

    public static <T> PaasResponnse<T> fail(T data) {
        return new PaasResponnse<>(ExceptionCode.ERROR.getCode(), ExceptionCode.ERROR.getName());
    }


}
