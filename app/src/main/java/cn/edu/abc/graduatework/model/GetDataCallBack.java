package cn.edu.abc.graduatework.model;


public interface GetDataCallBack<T> {

    void onSuccess(T object);

    void onFiled();
}
