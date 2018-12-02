package cn.edu.abc.graduatework.model;


import java.util.ArrayList;

import cn.edu.abc.graduatework.entity.Graduate;
import cn.edu.abc.graduatework.entity.News;
import cn.edu.abc.graduatework.entity.Result;
import cn.edu.abc.graduatework.entity.Topic;

public interface IHomeModel {


    /**
     * 获取轮播图的数据
     *
     * @param getDataCallBack 获取数据后的回调
     */
    void getBannerData(GetDataCallBack<ArrayList<String>> getDataCallBack);

    /**
     * 获取优秀毕业生数据
     *
     * @param getDataCallBack
     */
    void getGraduateDat(GetDataCallBack<ArrayList<Graduate>> getDataCallBack);

    /**
     * 获取热门话题
     *
     * @param getDataCallBack
     */
    void getHotTopicData(GetDataCallBack<Result<ArrayList<Topic>>> getDataCallBack);

    /**
     * 获取资讯
     *
     * @param getDataCallBack
     */
    void getNewsData(GetDataCallBack<Result<ArrayList<News>>> getDataCallBack);

}
