package cn.edu.abc.graduatework.model;


import cn.edu.abc.graduatework.entity.Result;
import cn.edu.abc.graduatework.entity.User;

public interface IUserModel {

   void login(User user,GetDataCallBack<Result<User>> getDataCallBack);

   void register(User user,GetDataCallBack<Result<String>> getDataCallBack);

   void getUserInfo(String userId,GetDataCallBack<Result<User>> getDataCallBack);





}
