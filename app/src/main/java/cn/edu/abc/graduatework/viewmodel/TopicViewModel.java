package cn.edu.abc.graduatework.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;

import cn.edu.abc.graduatework.entity.Article;
import cn.edu.abc.graduatework.entity.Result;
import cn.edu.abc.graduatework.entity.ResultEnum;
import cn.edu.abc.graduatework.entity.Topic;
import cn.edu.abc.graduatework.model.GetDataCallBack;
import cn.edu.abc.graduatework.model.ITopicModel;
import cn.edu.abc.graduatework.model.imp.TopicModelImp;

public class TopicViewModel extends ViewModel {


    private MutableLiveData<Result<ArrayList<Article>>> mLiveData = new MutableLiveData<>();
    private MutableLiveData<Result<ArrayList<Topic>>> mMyTopicLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> mCurrTabType = new MutableLiveData<>();

    private ITopicModel mTopicModel = new TopicModelImp();

    public MutableLiveData<Result<ArrayList<Topic>>> getMyTopicLiveData() {
        return mMyTopicLiveData;
    }

    public MutableLiveData<Integer> getCurrTabType() {
        return mCurrTabType;
    }

    public MutableLiveData<Result<ArrayList<Article>>> getArticleLiveData() {
        return mLiveData;
    }


    public void fetchMyTopic(String userId) {
        mTopicModel.getMyTopic(userId, new GetDataCallBack<Result<ArrayList<Topic>>>() {
            @Override
            public void onSuccess(Result<ArrayList<Topic>> object) {
                mMyTopicLiveData.setValue(object);
            }

            @Override
            public void onFiled() {
                mMyTopicLiveData.setValue(new Result<ArrayList<Topic>>(ResultEnum.FAILED.getCode(), "请求失败，请检查网络"));
            }
        });
    }

    public void fetchNewPublish(String topicId) {
        mTopicModel.getNewPublishData(topicId, new GetDataCallBack<Result<ArrayList<Article>>>() {
            @Override
            public void onSuccess(Result<ArrayList<Article>> object) {
                mLiveData.setValue(object);

            }

            @Override
            public void onFiled() {

            }
        });
    }

    public void fetchHotArticle(String topicId) {
        mTopicModel.getHotArticleData(topicId, new GetDataCallBack<Result<ArrayList<Article>>>() {
            @Override
            public void onSuccess(Result<ArrayList<Article>> object) {
                mLiveData.setValue(object);

            }

            @Override
            public void onFiled() {

            }
        });
    }

    public void fetchNewReply(String topicId) {
        mTopicModel.getNewReplyData(topicId, new GetDataCallBack<Result<ArrayList<Article>>>() {
            @Override
            public void onSuccess(Result<ArrayList<Article>> object) {
                mLiveData.setValue(object);

            }

            @Override
            public void onFiled() {

            }
        });
    }

}
