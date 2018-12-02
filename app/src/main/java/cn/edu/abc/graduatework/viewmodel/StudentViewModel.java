package cn.edu.abc.graduatework.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;

import cn.edu.abc.graduatework.entity.Graduate;
import cn.edu.abc.graduatework.model.GetDataCallBack;
import cn.edu.abc.graduatework.model.IGraduateModel;
import cn.edu.abc.graduatework.model.imp.GraduateModelImp;

public class StudentViewModel extends ViewModel {


    private MutableLiveData<ArrayList<Graduate>> mMutableLiveData = new MutableLiveData<>();

    private IGraduateModel mGraduateModel = new GraduateModelImp();

    public MutableLiveData<ArrayList<Graduate>> getMutableLiveData() {
        return mMutableLiveData;
    }

    public void fetch() {
        mGraduateModel.getData(new GetDataCallBack<ArrayList<Graduate>>() {
            @Override
            public void onSuccess(ArrayList<Graduate> object) {
                mMutableLiveData.setValue(object);
            }
            @Override
            public void onFiled() {
            }
        });
    }

}
