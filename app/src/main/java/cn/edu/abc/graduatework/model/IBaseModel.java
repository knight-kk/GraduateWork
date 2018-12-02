package cn.edu.abc.graduatework.model;


public interface IBaseModel<T> {

    void getData(GetDataCallBack<T> callBack);
}
