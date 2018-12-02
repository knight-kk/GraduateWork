package cn.edu.abc.graduatework.model.imp;


import cn.edu.abc.graduatework.model.GetDataCallBack;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaseModel<T> {

    public Callback<T> getCallback(final GetDataCallBack<T> getDataCallBack) {
        return new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                getDataCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                getDataCallBack.onFiled();
            }
        };
    }

}
