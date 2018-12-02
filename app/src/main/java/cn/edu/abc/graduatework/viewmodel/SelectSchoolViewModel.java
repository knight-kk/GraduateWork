package cn.edu.abc.graduatework.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;

import cn.edu.abc.graduatework.entity.School;
import cn.edu.abc.graduatework.model.GetDataCallBack;
import cn.edu.abc.graduatework.model.IBaseModel;
import cn.edu.abc.graduatework.model.imp.SelectSchoolModelImp;


public class SelectSchoolViewModel extends ViewModel {

    private MutableLiveData<ArrayList<School>> mListMutableLiveData = new MutableLiveData<>();
    private IBaseModel mIBaseModel = new SelectSchoolModelImp();

    public MutableLiveData<ArrayList<School>> getListMutableLiveData() {
        return mListMutableLiveData;
    }

    public void fetch() {
        mIBaseModel.getData(new GetDataCallBack<ArrayList<School>>() {
            @Override
            public void onSuccess(ArrayList<School> object) {
                mListMutableLiveData.setValue(object);
            }

            @Override
            public void onFiled() {

            }
        });
    }

}
