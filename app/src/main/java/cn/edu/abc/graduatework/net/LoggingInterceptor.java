package cn.edu.abc.graduatework.net;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class LoggingInterceptor implements Interceptor {

    private static final String TAG = "LoggingInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String method = request.method();
        Response response = chain.proceed(chain.request());
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();

        Log.i(TAG, " " + request.toString());
        Log.d(TAG, "| Response:" + content);
        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    }
}
