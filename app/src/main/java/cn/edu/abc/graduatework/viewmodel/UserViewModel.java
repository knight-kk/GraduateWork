package cn.edu.abc.graduatework.viewmodel;


import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import cn.edu.abc.graduatework.entity.Result;
import cn.edu.abc.graduatework.entity.ResultEnum;
import cn.edu.abc.graduatework.entity.User;
import cn.edu.abc.graduatework.model.GetDataCallBack;
import cn.edu.abc.graduatework.model.IUserModel;
import cn.edu.abc.graduatework.model.imp.UserModelImp;

public class UserViewModel extends ViewModel {

    private MutableLiveData<Result<User>> mLiveData = new MutableLiveData<>();
    private MutableLiveData<Result<String>> mRegisterLiveData = new MutableLiveData<>();


    public MutableLiveData<Result<String>> getRegisterLiveData() {
        return mRegisterLiveData;
    }

    private IUserModel mUserModel = new UserModelImp();


    public MutableLiveData<Result<User>> getLiveData() {
        return mLiveData;
    }


    public void login(String email, String password) {
        final User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        mUserModel.login(user, new GetDataCallBack<Result<User>>() {
            @Override
            public void onSuccess(Result<User> object) {
                mLiveData.setValue(object);
            }

            @Override
            public void onFiled() {
                mLiveData.setValue(new Result<User>(ResultEnum.FAILED.getCode(), "请求失败，请检查网络"));
            }
        });
    }

    /** 注册
     * @param userName 用户名
     * @param email 邮箱
     * @param password 密码
     */
    public void register(String userName, String email, String password) {
        final User user = new User();
        user.setName(userName);
        user.setEmail(email);
        user.setPassword(password);
        mUserModel.register(user, new GetDataCallBack<Result<String>>() {
            @Override
            public void onSuccess(Result<String> object) {
                mRegisterLiveData.setValue(object);
            }
            @Override
            public void onFiled() {
                mLiveData.setValue(new Result<User>(ResultEnum.FAILED.getCode(), "请求失败，请检查网络"));
            }
        });
    }


    public void getUserInfo(String userId) {
        mUserModel.getUserInfo(userId, new GetDataCallBack<Result<User>>() {
            @Override
            public void onSuccess(Result<User> object) {
                mLiveData.setValue(object);
            }

            @Override
            public void onFiled() {
                mLiveData.setValue(new Result<User>(ResultEnum.FAILED.getCode(), "请求失败，请检查网络"));
            }
        });
    }


}
