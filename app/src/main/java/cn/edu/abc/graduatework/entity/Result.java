package cn.edu.abc.graduatework.entity;


public class Result<T> {

    private int code;

    private String msg;

    private T value;

    public int getCode() {
        return code;
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
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

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
