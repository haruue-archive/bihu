package cn.com.caoyue.bihu.data.network;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

public class RestApi {

    private static ApiService apiService;

    public static ApiService getApiService() {
        if (null == apiService) init();
        return apiService;
    }

    private static void init() {

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd' 'HH:mm:ss")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://120.27.113.124/api/know_web/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        apiService = retrofit.create(ApiService.class);

    }
}
