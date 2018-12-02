package cn.edu.abc.graduatework.model.imp;


import java.util.ArrayList;

import cn.edu.abc.graduatework.entity.School;
import cn.edu.abc.graduatework.model.GetDataCallBack;
import cn.edu.abc.graduatework.model.IBaseModel;

public class SelectSchoolModelImp implements IBaseModel<ArrayList<School>> {
    @Override
    public void getData(GetDataCallBack<ArrayList<School>> callBack) {
        ArrayList<School> schools = new ArrayList<>();
        schools.add(new School("安徽商贸职业技术学院","1"));
        schools.add(new School("安徽大学","2"));
        schools.add(new School("安徽农业大学","3"));
        schools.add(new School("安徽理工大学","4"));
        schools.add(new School("安徽医科大学","5"));
        schools.add(new School("安徽建筑大学","6"));
        schools.add(new School("安徽建筑大学","7"));
        schools.add(new School("安徽中医药大学","8"));
        schools.add(new School("北京大学","1"));
        schools.add(new School("北京第二外国语学院","1"));
        schools.add(new School("北京电影学院","1"));
        schools.add(new School("北京交通大学","1"));
        schools.add(new School("长安大学","1"));
        schools.add(new School("成都理工大学","1"));
        schools.add(new School("大连海事大学","1"));
        schools.add(new School("第二军医大学","1"));
        schools.add(new School("东北财经大学"));
        schools.add(new School("东北大学"));
        schools.add(new School("东北师范大学"));
        schools.add(new School("阜阳师范学院"));
        schools.add(new School("复旦大学"));
        schools.add(new School("复旦大学"));


        callBack.onSuccess(schools);

    }
}
