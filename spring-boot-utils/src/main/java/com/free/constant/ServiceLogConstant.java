package com.free.constant;

/**
 * @ClassName ServiceLogConstant
 * @Description 日志记录常理类
 * @Author hsiangcn@sina.com
 * @Date 2018/11/28 15:42
 * @Version 1.0
 */
public enum ServiceLogConstant {

    EXECUTE_STATUS_SUCCESS("SUCCESS"),
    EXECUTE_STATUS_FAILED("FAILED");

    private String code;

    private ServiceLogConstant(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
