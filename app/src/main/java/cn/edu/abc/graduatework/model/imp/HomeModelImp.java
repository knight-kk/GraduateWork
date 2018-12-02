package cn.edu.abc.graduatework.model.imp;


import java.util.ArrayList;

import cn.edu.abc.graduatework.entity.Graduate;
import cn.edu.abc.graduatework.entity.News;
import cn.edu.abc.graduatework.entity.Result;
import cn.edu.abc.graduatework.entity.Topic;
import cn.edu.abc.graduatework.model.GetDataCallBack;
import cn.edu.abc.graduatework.model.IHomeModel;
import cn.edu.abc.graduatework.net.RetrofitClient;

public class HomeModelImp implements IHomeModel {
    @Override
    public void getBannerData(GetDataCallBack<ArrayList<String>> getDataCallBack) {


    }

    @Override
    public void getGraduateDat(GetDataCallBack<ArrayList<Graduate>> getDataCallBack) {
        ArrayList<Graduate> graduates = new ArrayList<>();
        graduates.add(new Graduate("陈华峰", "http://owf9dklao.bkt.clouddn.com/picgo/20181017215318.jpg", "安徽商贸职业技术学院","陈华峰，女，汉族，安徽安庆人，中共预备党员，1995年12月出生，2016年9月至今就读于安徽商贸职业技术学院电子信息工程系电子商务技术专业。在校期间，曾获国家励志奖学金、学院一等奖学金、二等奖学金；获全国职业技能大赛高职组团体赛一等奖等奖项；"));
        graduates.add(new Graduate("马化腾", "https://dwz.cn/UEmbAk76", "深圳大学计算机与软件学院","马化腾，生于海南岛东方县八所港，广东潮阳县人，是广东深圳腾讯公司现任董事会主席兼首席运行官、现任全国人大代表。"));
        graduates.add(new Graduate("李彦宏", "https://dwz.cn/378lRpr5", "北京大学","李彦宏，中国山西阳泉人，研究生学历。百度公司的创建者，现任董事长兼首席执行官以及全国工商联副主席。"));
        graduates.add(new Graduate("俞敏洪", "https://dwz.cn/ZGmYVIgd", "北京大学"));
        graduates.add(new Graduate("吴佳俊", "https://dwz.cn/OAYQwbpu", "清华大学"));
        getDataCallBack.onSuccess(graduates);


    }

    @Override
    public void getHotTopicData(GetDataCallBack<Result<ArrayList<Topic>>> getDataCallBack) {
//        ArrayList<Topic> topics = new ArrayList<>();
////        topics.add(new Topic("程序员", "https://upload.jianshu.io/collections/images/16/computer_guy.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/180/h/180"));
////        topics.add(new Topic("产品", "https://upload.jianshu.io/collections/images/264569/2.pic.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/240/h/240"));
////        topics.add(new Topic("创业", "https://upload.jianshu.io/collections/images/26/android.graphics.Bitmap_34d0eb2.jpeg?imageMogr2/auto-orient/strip|imageView2/1/w/180/h/180"));
////        topics.add(new Topic("安徽圈", "https://upload.jianshu.io/collections/images/279215/%E5%AE%89%E5%BE%BD.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/180/h/180"));
////        topics.add(new Topic("旅行", "https://upload.jianshu.io/collections/images/13/%E5%95%8A.png?imageMogr2/auto-orient/strip|imageView2/1/w/180/h/180"));
////        topics.add(new Topic("上班族", "https://upload.jianshu.io/collections/images/28/%E4%B8%8A%E7%8F%AD%E8%BF%99%E7%82%B9%E4%BA%8B%E5%84%BF.jpeg?imageMogr2/auto-orient/strip|imageView2/1/w/180/h/180"));
////
//        getDataCallBack.onSuccess(topics);
        RetrofitClient.getApiService().getTopicList().enqueue(
                new RetrofitClient.MyCallback<>(getDataCallBack));
    }

    @Override
    public void getNewsData(GetDataCallBack<Result<ArrayList<News>>> getDataCallBack) {
//        ArrayList<News> news = new ArrayList<>();
//        news.add(new News("2018Android毕业生须要掌握的知识点", "https://img1.mukewang.com/5b14d44b00014f9606000338-240-135.jpg", "2018.10.12 16:04", 43, "", "", ""));
//        news.add(new News("2018校招Java面试宝典", "https://img1.mukewang.com/5bbafd180001688d06000338-240-135.jpg", "2018.10.12 13:55", 55, "", "", ""));
//        news.add(new News("测试数据测试数据测试数据测试数据", "https://img3.mukewang.com/5b9a01a40001fe1805400300-240-135.jpg", "2018.10.12 11:43", 54, "", "", ""));
//        news.add(new News("三星高管透露：Galaxy F是折叠手机又是平板电脑", "http://07imgmini.eastday.com//mobile//20181015//20181015161238_4644d3b7265505ce2b1e2a932fbf99a6_1_mwpm_03200403.jpg", "2018.10.12 10:25", 67, "", "", ""));
//        news.add(new News("国科大40年培养108位院士 校庆获赠同名小行星", "https://img.mukewang.com/5bbc52f60001d1be06000338-240-135.jpg", "2018.10.12 9:17", 156, "", "", ""));
//        news.add(new News("又是一年毕业季", "https://img4.mukewang.com/5b9f578c00011ee106000338-240-135.jpg", "2018.10.8 8:46", 243, "", "", ""));
//        news.add(new News("模拟数据测试数据", "https://img1.mukewang.com/5b14d44b00014f9606000338-240-135.jpg", "2018.10.8 9:34", 12, "", "", ""));
        RetrofitClient.getApiService().getNews("0").enqueue(new RetrofitClient.MyCallback<ArrayList<News>>(getDataCallBack));
    }
}
