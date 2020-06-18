package com.paas.service.common.exception;

/**
 * create by sumerian on 2020/6/2
 * <p>
 * desc:
 **/
public enum ExceptionCode {

    //
    SUCCESS("sys-00-000", "success"),
    //
    ERROR("sys-00-001", "系統異常");
    private String code;

    private String name;

    ExceptionCode(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
