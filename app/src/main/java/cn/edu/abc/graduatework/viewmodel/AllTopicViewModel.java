package cn.edu.abc.graduatework.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;

import cn.edu.abc.graduatework.entity.Result;
import cn.edu.abc.graduatework.entity.ResultEnum;
import cn.edu.abc.graduatework.entity.Topic;
import cn.edu.abc.graduatework.model.GetDataCallBack;
import cn.edu.abc.graduatework.model.ITopicModel;
import cn.edu.abc.graduatework.model.imp.TopicModelImp;

public class AllTopicViewModel extends ViewModel {

    private MutableLiveData<Result<ArrayList<Topic>>> mMutableLiveData = new MutableLiveData<>();

    private ITopicModel mTopicModel = new TopicModelImp();

    public MutableLiveData<Result<ArrayList<Topic>>> getMutableLiveData() {
        return mMutableLiveData;
    }


    public void loadData() {
        mTopicModel.getData(new GetDataCallBack<Result<ArrayList<Topic>>>() {
            @Override
            public void onSuccess(Result<ArrayList<Topic>> object) {
                mMutableLiveData.setValue(object);
            }

            @Override
            public void onFiled() {
                mMutableLiveData.setValue(new Result<ArrayList<Topic>>(ResultEnum.FAILED.getCode(), "请求失败，请检查网络"));
            }
        });
    }
}
