package cn.edu.abc.graduatework.model.imp;

import java.util.ArrayList;

import cn.edu.abc.graduatework.entity.News;
import cn.edu.abc.graduatework.model.GetDataCallBack;
import cn.edu.abc.graduatework.model.IBaseModel;


public class GraduateNewsModelIpm implements IBaseModel<ArrayList<News>> {
    private static final String TAG = "GraduateNewsModelIpm";

    @Override
    public void getData(final GetDataCallBack<ArrayList<News>> callBack) {
        ArrayList<News> news = new ArrayList<>();
        news.add(new News("2018Android毕业生须要掌握的知识点", "https://img1.mukewang.com/5b14d44b00014f9606000338-240-135.jpg", "2018.10.12 16:04", 43, "", "", ""));
        news.add(new News("2018校招Java面试宝典", "https://img1.mukewang.com/5bbafd180001688d06000338-240-135.jpg", "2018.10.12 13:55", 55, "", "", ""));
        news.add(new News("测试数据测试数据测试数据测试数据", "https://img3.mukewang.com/5b9a01a40001fe1805400300-240-135.jpg", "2018.10.12 11:43", 54, "", "", ""));
        news.add(new News("三星高管透露：Galaxy F是折叠手机又是平板电脑", "http://07imgmini.eastday.com//mobile//20181015//20181015161238_4644d3b7265505ce2b1e2a932fbf99a6_1_mwpm_03200403.jpg", "2018.10.12 10:25", 67, "", "", ""));
        news.add(new News("国科大40年培养108位院士 校庆获赠同名小行星", "https://img.mukewang.com/5bbc52f60001d1be06000338-240-135.jpg", "2018.10.12 9:17", 156, "", "", ""));
        news.add(new News("又是一年毕业季", "https://img4.mukewang.com/5b9f578c00011ee106000338-240-135.jpg", "2018.10.8 8:46", 243, "", "", ""));
        news.add(new News("模拟数据测试数据", "https://img1.mukewang.com/5b14d44b00014f9606000338-240-135.jpg", "2018.10.8 9:34", 12, "", "", ""));

        callBack.onSuccess(news);


    }
}
