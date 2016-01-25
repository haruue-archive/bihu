package cn.com.caoyue.bihu.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.com.caoyue.bihu.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        ((AppCompatActivity) context).startActivity(intent);
    }
}
