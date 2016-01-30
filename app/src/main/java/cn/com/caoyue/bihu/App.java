package cn.com.caoyue.bihu;

import android.app.Application;
import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;

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
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }
}
