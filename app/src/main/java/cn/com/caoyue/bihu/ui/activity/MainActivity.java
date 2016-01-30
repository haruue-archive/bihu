package cn.com.caoyue.bihu.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.activeandroid.ActiveAndroid;
import com.jude.utils.JActivityManager;
import com.jude.utils.JUtils;

import cn.com.caoyue.bihu.BuildConfig;
import cn.com.caoyue.bihu.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize JUtils & JActivityManager
        JUtils.initialize(getApplication());
        JUtils.setDebug(BuildConfig.DEBUG, "inMain");
        JActivityManager.getInstance().pushActivity(MainActivity.this);
        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_inMainActivity);
        setSupportActionBar(toolbar);
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        ((AppCompatActivity) context).startActivity(intent);
    }
}
