package cn.edu.abc.graduatework.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;

import cn.edu.abc.graduatework.entity.Graduate;
import cn.edu.abc.graduatework.entity.News;
import cn.edu.abc.graduatework.entity.Result;
import cn.edu.abc.graduatework.entity.Topic;
import cn.edu.abc.graduatework.model.GetDataCallBack;
import cn.edu.abc.graduatework.model.IHomeModel;
import cn.edu.abc.graduatework.model.imp.HomeModelImp;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<Result<ArrayList<News>>> mNewsLiveData = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Graduate>> mGraduateLiveData = new MutableLiveData<>();
    private MutableLiveData<ArrayList<String>> mBannerLiveData = new MutableLiveData<>();
    private MutableLiveData<Result<ArrayList<Topic>>> mHotTopicLiveData = new MutableLiveData<>();

    private IHomeModel mHomeModel = new HomeModelImp();

    public MutableLiveData<Result<ArrayList<News>>> getNewsLiveData() {
        return mNewsLiveData;
    }

    public MutableLiveData<ArrayList<Graduate>> getGraduateLiveData() {
        return mGraduateLiveData;
    }

    public MutableLiveData<ArrayList<String>> getBannerLiveData() {
        return mBannerLiveData;
    }

    public MutableLiveData<Result<ArrayList<Topic>>> getHotTopicLiveData() {
        return mHotTopicLiveData;
    }

    public void fetchNews() {
        mHomeModel.getNewsData(new GetDataCallBack<Result<ArrayList<News>>>() {
            @Override
            public void onSuccess(Result<ArrayList<News>> object) {
                mNewsLiveData.setValue(object);
            }

            @Override
            public void onFiled() {

            }
        });
    }


    public void fetchHotTopic() {
        mHomeModel.getHotTopicData(new GetDataCallBack<Result<ArrayList<Topic>>>() {
            @Override
            public void onSuccess(Result<ArrayList<Topic>> object) {
                mHotTopicLiveData.setValue(object);
            }

            @Override
            public void onFiled() {

            }
        });
    }


    public void fetchGraduate() {
        mHomeModel.getGraduateDat(new GetDataCallBack<ArrayList<Graduate>>() {
            @Override
            public void onSuccess(ArrayList<Graduate> object) {
                mGraduateLiveData.setValue(object);
            }

            @Override
            public void onFiled() {

            }
        });
    }


}
