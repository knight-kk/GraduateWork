package cn.edu.abc.graduatework.net;


import java.util.concurrent.TimeUnit;

import cn.edu.abc.graduatework.entity.Result;
import cn.edu.abc.graduatework.model.GetDataCallBack;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
//    private static final String sBaseUrl = "http://192.168.1.26:8080/";
    private static final String sBaseUrl = "http://114.67.232.55:8080/";

    private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient.Builder()
            .connectTimeout(120, TimeUnit.SECONDS)
            .addInterceptor(new LoggingInterceptor())
            .build();


    private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
            .baseUrl(sBaseUrl)
            .client(OK_HTTP_CLIENT)
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    private static final ApiService API_SERVICE =
            RETROFIT_CLIENT.create(ApiService.class);


    public static ApiService getApiService() {
        return API_SERVICE;
    }

    public static class MyCallback<T> implements Callback<Result<T>> {
        GetDataCallBack<Result<T>> getDataCallBack;

        public MyCallback(GetDataCallBack<Result<T>> getDataCallBack) {
            this.getDataCallBack = getDataCallBack;
        }

        @Override
        public void onResponse(Call<Result<T>> call, Response<Result<T>> response) {
            if (response.isSuccessful()) {
                getDataCallBack.onSuccess(response.body());
            } else {
                getDataCallBack.onFiled();

            }
        }

        @Override
        public void onFailure(Call<Result<T>> call, Throwable t) {
            getDataCallBack.onFiled();

        }
    }


}
