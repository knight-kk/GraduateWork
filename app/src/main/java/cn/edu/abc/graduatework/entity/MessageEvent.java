package cn.edu.abc.graduatework.entity;


public class MessageEvent<T> {

    private String message;
    private T value;

    public MessageEvent() {
    }

    public MessageEvent(String message, T value) {
        this.message = message;
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
