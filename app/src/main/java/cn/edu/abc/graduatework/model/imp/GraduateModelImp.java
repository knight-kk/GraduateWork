package cn.edu.abc.graduatework.model.imp;


import java.util.ArrayList;

import cn.edu.abc.graduatework.entity.Graduate;
import cn.edu.abc.graduatework.model.GetDataCallBack;
import cn.edu.abc.graduatework.model.IGraduateModel;

public class GraduateModelImp implements IGraduateModel {
    @Override
    public void getData(GetDataCallBack<ArrayList<Graduate>> callBack) {
        ArrayList<Graduate> graduates = new ArrayList<>();
        graduates.add(new Graduate("张三", "http://img2.imgtn.bdimg.com/it/u=860868304,2275218400&fm=26&gp=0.jpg",
                "电子商务技术", "安徽金点子有限公司", "android工程师"));
        graduates.add(new Graduate("胡博涵", "http://img2.imgtn.bdimg.com/it/u=4096415375,4026936163&fm=26&gp=0.jpg",
                "市场营销", "上海春晖有限公司", "销售员"));
        graduates.add(new Graduate("陈柔晴", "http://img5.imgtn.bdimg.com/it/u=427822075,2337651982&fm=26&gp=0.jpg",
                "会计专业", "湖南创新有限公司", "会计"));
        graduates.add(new Graduate("陈岩", "http://img4.imgtn.bdimg.com/it/u=2395133928,460219354&fm=11&gp=0.jpg",
                "物流管理专业", "菜鸟驿站", "菜鸟驿站员工"));
        graduates.add(new Graduate("王雨辰", "http://img3.imgtn.bdimg.com/it/u=699801082,4208454093&fm=26&gp=0.jpg",
                "计算机应用技术", "北京创新有限公司", "java工程师"));
        graduates.add(new Graduate("梁洲", "http://img4.imgtn.bdimg.com/it/u=96319522,3090497352&fm=26&gp=0.jpg",
                "法律事务", "江苏信赖事务所", "法律咨询顾问"));
        graduates.add(new Graduate("苏芷琳", "http://img3.imgtn.bdimg.com/it/u=699801082,4208454093&fm=26&gp=0.jpg",
                "艺术设计", "北京春晖有限公司", "UI设计师"));
        callBack.onSuccess(graduates);

    }
}
