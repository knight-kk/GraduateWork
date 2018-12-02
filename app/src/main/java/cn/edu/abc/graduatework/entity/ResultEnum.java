package cn.edu.abc.graduatework.entity;

public enum ResultEnum {

    UNKONW_ERROR(-1, "未知错误"),
    SUCCESS(0, "success"),
    FAILED(1,"failed"),
    LOSE_PARAM(2,"缺少参数");



    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
