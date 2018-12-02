package cn.edu.abc.graduatework.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import cn.edu.abc.graduatework.entity.News;
import cn.edu.abc.graduatework.model.GetDataCallBack;
import cn.edu.abc.graduatework.model.imp.GraduateNewsModelIpm;

import java.util.ArrayList;

public class GraduateNewsViewModel extends ViewModel {
    private static final String TAG = "GraduateNewsViewModel";

    private MutableLiveData<ArrayList<News>> mMutableLiveData = new MutableLiveData<>();

    private GraduateNewsModelIpm mGraduateNewsModelIpm = new GraduateNewsModelIpm();

    public void fetch() {
        mGraduateNewsModelIpm.getData(new GetDataCallBack<ArrayList<News>>() {
            @Override
            public void onSuccess(final ArrayList<News> object) {

                        mMutableLiveData.setValue(object);
                Log.i(TAG, "onSuccess: ");


            }

            @Override
            public void onFiled() {

            }
        });
    }

    public MutableLiveData<ArrayList<News>> getMutableLiveData() {
        return mMutableLiveData;
    }
}
