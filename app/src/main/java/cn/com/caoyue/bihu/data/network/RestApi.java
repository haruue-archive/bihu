package cn.com.caoyue.bihu.data.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

public class RestApi {

    private static ApiService haruueKnowWebApiService;
    private static ApiService haruueStorageApiService;

    public static ApiService getHaruueKnowWebApiService() {
        return haruueKnowWebApiService;
    }

    public static ApiService getHaruueStorageApiService() {
        return haruueStorageApiService;
    }

    public static void init() {

        Retrofit retrofitBuilderForKnowWebApi = new Retrofit.Builder()
                .baseUrl("https://vps.caoyue.com.cn/api/know_web/")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();

        haruueKnowWebApiService = retrofitBuilderForKnowWebApi.create(ApiService.class);

        Retrofit retrofitBuilderForStorageApi = new Retrofit.Builder()
                .baseUrl("https://api.caoyue.com.cn/know_web/image_api/")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();

        haruueStorageApiService = retrofitBuilderForStorageApi.create(ApiService.class);

    }
}
