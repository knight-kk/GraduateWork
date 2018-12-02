package cn.edu.abc.graduatework.model.imp;


import java.util.ArrayList;

import cn.edu.abc.graduatework.entity.Article;
import cn.edu.abc.graduatework.entity.Result;
import cn.edu.abc.graduatework.entity.Topic;
import cn.edu.abc.graduatework.model.GetDataCallBack;
import cn.edu.abc.graduatework.model.ITopicModel;
import cn.edu.abc.graduatework.net.RetrofitClient;

public class TopicModelImp implements ITopicModel {

    @Override
    public void getData(GetDataCallBack<Result<ArrayList<Topic>>> callBack) {
        RetrofitClient.getApiService().getTopicList().enqueue(
                new RetrofitClient.MyCallback<>(callBack));
    }


    @Override
    public void getMyTopic(String userId, GetDataCallBack<Result<ArrayList<Topic>>> getDataCallBack) {
        RetrofitClient.getApiService().getMyTopicList(userId).enqueue(
                new RetrofitClient.MyCallback<>(getDataCallBack));
    }

    @Override
    public void getNewPublishData(String topicId,GetDataCallBack<Result<ArrayList<Article>>> getDataCallBack) {
        RetrofitClient.getApiService().getArticleNewList(topicId)
                .enqueue(new RetrofitClient.MyCallback<ArrayList<Article>>(getDataCallBack));
    }

    @Override
    public void getHotArticleData(String topicId,GetDataCallBack<Result<ArrayList<Article>>> getDataCallBack) {
           RetrofitClient.getApiService().getArticleHotList(topicId)
                .enqueue(new RetrofitClient.MyCallback<ArrayList<Article>>(getDataCallBack));
    }

    @Override
    public void getNewReplyData(String topicId,GetDataCallBack<Result<ArrayList<Article>>> getDataCallBack) {
          RetrofitClient.getApiService().getArticleNewReply(topicId)
                .enqueue(new RetrofitClient.MyCallback<ArrayList<Article>>(getDataCallBack));
    }


}
