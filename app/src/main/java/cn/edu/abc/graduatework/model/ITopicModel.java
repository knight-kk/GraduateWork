package cn.edu.abc.graduatework.model;


import java.util.ArrayList;

import cn.edu.abc.graduatework.entity.Article;
import cn.edu.abc.graduatework.entity.Result;
import cn.edu.abc.graduatework.entity.Topic;

public interface ITopicModel extends IBaseModel<Result<ArrayList<Topic>>>{


    void getMyTopic(String userId,GetDataCallBack<Result<ArrayList<Topic>>> getDataCallBack);

    void getNewPublishData(String topicId,GetDataCallBack<Result<ArrayList<Article>>> getDataCallBack);

    void getHotArticleData(String topicId,GetDataCallBack<Result<ArrayList<Article>>> getDataCallBack);

    void getNewReplyData(String topicId,GetDataCallBack<Result<ArrayList<Article>>> getDataCallBack);

}
