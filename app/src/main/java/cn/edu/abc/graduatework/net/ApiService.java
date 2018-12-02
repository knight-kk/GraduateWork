package cn.edu.abc.graduatework.net;


import java.util.ArrayList;

import cn.edu.abc.graduatework.entity.Article;
import cn.edu.abc.graduatework.entity.Comment;
import cn.edu.abc.graduatework.entity.Graduate;
import cn.edu.abc.graduatework.entity.News;
import cn.edu.abc.graduatework.entity.Result;
import cn.edu.abc.graduatework.entity.Topic;
import cn.edu.abc.graduatework.entity.User;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface


ApiService {

//--------------------用户--------------------

    @POST("user/register")
    Call<Result<String>> register(@Body User user);//注册

    @POST("user/login")
    Call<Result<User>> login(@Body User user);// 登录

    @GET("user/{id}")
    Call<Result<User>> getUserInfo(@Path("id") String id);// 获取用户信息

    @POST("user/perfectInfo")
    Call<Result<String>> perfectInfo(@Body User user);//完善个人信息

    @POST("upload")
    @Multipart
    Call<Result<String>> upload(@Part MultipartBody.Part file);//文件上传


    //--------------------话题--------------------

    @POST("topic/create")
    Call<Result<Topic>> createTopic(@Body Topic topic);

    @GET("topic/list")
    Call<Result<ArrayList<Topic>>> getTopicList();

    @GET("topic/list/my/{userId}")
    Call<Result<ArrayList<Topic>>> getMyTopicList(@Path("userId") String userId);

    @GET("topic/{topicId}/{userId}")
    Call<Result<Topic>> getTopicInfo(@Path("topicId") String topicId, @Path("userId") String userId);

    @GET("topic/follow/{topicId}/{userId}")
    Call<Result<String>> followTopic(@Path("topicId") String topicId, @Path("userId") String userId);

    @GET("/list/myCreate/{userId}")
    Call<Result<ArrayList<Topic>>> getMyCreateTop(@Path("userId") String userId);


    //--------------------文章--------------------

    @POST("article/create")
    Call<Result<Article>> addArticle(@Body Article article);

    @GET("article/getNewList{topicId}")
    Call<Result<ArrayList<Article>>> getArticleNewList(@Path(value = "topicId") String topicId);

    @GET("article/getNewReply{topicId}")
    Call<Result<ArrayList<Article>>> getArticleNewReply(@Path(value = "topicId") String topicId);

    @GET("article/getHotList{topicId}")
    Call<Result<ArrayList<Article>>> getArticleHotList(@Path(value = "topicId") String topicId);

    @GET("article/collection/{articleId}/{userId}")
    Call<Result<String>> articleCollection(@Path(value = "articleId") String articleId, @Path(value = "userId") String userId);

    @GET("article/getCollection/{userId}")
    Call<Result<ArrayList<Article>>> getCollection(@Path(value = "userId") String userId);

    @GET("article/getMyArticle/{userId}")
    Call<Result<ArrayList<Article>>> getMyArticle(@Path(value = "userId") String userId);

    //    评论
    @POST("comment/create")
    Call<Result<Comment>> addComment(@Body Comment comment);

    @GET("comment/list/{articleId}")
    Call<Result<ArrayList<Comment>>> getCommentList(@Path("articleId") String articleId);

    @GET("comment/reply/list/{commentId}")
    Call<Result<ArrayList<Comment>>> getReplyList(@Path("commentId") String commentId);

//    毕业生

    @GET("graduate/list/{schoolId}")
    Call<Result<ArrayList<Graduate>>> getGraduateList(@Path("schoolId") String schoolId);

//    @GET("graduate/newslist/{schoolId}")
//    public Result getNewsList(@Path("schoolId") String schoolId);

    @GET("news/newsList/{schoolId}")
    Call<Result<ArrayList<News>>> getNews(@Path("schoolId") String schoolId);





}
