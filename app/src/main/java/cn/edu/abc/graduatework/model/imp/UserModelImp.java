package cn.edu.abc.graduatework.model.imp;


import cn.edu.abc.graduatework.entity.Result;
import cn.edu.abc.graduatework.entity.User;
import cn.edu.abc.graduatework.model.GetDataCallBack;
import cn.edu.abc.graduatework.model.IUserModel;
import cn.edu.abc.graduatework.net.RetrofitClient;

public class UserModelImp implements IUserModel {

    @Override
    public void login(User user, final GetDataCallBack<Result<User>> getDataCallBack) {

        RetrofitClient.getApiService().login(user).enqueue(new RetrofitClient.MyCallback<User>(getDataCallBack));
    }

    @Override
    public void register(User user, final GetDataCallBack<Result<String>> getDataCallBack) {
        RetrofitClient.getApiService().register(user)
                .enqueue(new RetrofitClient.MyCallback<String>(getDataCallBack));
    }

    @Override
    public void getUserInfo(String userId, GetDataCallBack<Result<User>> getDataCallBack) {
        RetrofitClient.getApiService().getUserInfo(userId)
                .enqueue(new RetrofitClient.MyCallback<User>(getDataCallBack));
    }

}
