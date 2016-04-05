package cn.com.caoyue.bihu;

import android.app.Application;
import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.jude.utils.JUtils;

import cn.com.caoyue.imageloader.ImageLoaderConfig;

import cn.com.caoyue.bihu.data.network.RestApi;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize ActiveAndroid
        Configuration dbConfiguration = new Configuration.Builder(this).setDatabaseName("bihu.db").setDatabaseVersion(2).create();
        ActiveAndroid.initialize(dbConfiguration);
        // Initialize Retrofit
        RestApi.init();
        // Initialize Haruue Image Loader
        ImageLoaderConfig.start(this)
                .setDefaultDrawableOnLoading(R.drawable.default_face)
                .setDefaultDrawableOnFailure(R.drawable.default_face)
                .setCachePath(getCacheDir().getPath() + "/imageCache")
                .setDefaultFillView()
                .build();
        Log.d("imageLoaderCacheDir", getCacheDir().getPath() + "imageCache");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }
}
