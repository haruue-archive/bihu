package cn.com.caoyue.bihu.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.jude.utils.JActivityManager;
import com.jude.utils.JUtils;

import cn.com.caoyue.bihu.BuildConfig;
import cn.com.caoyue.bihu.R;
import cn.com.caoyue.bihu.data.storage.CurrentFragment;
import cn.com.caoyue.bihu.data.storage.CurrentSelectImageBitmap;
import cn.com.caoyue.bihu.ui.fragment.HomeFragment;
import cn.com.caoyue.bihu.ui.fragment.ModifyFaceFragment;
import cn.com.caoyue.bihu.ui.navigation.NavManager;
import cn.com.caoyue.bihu.util.GetAlbumPicture;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    FragmentManager fragmentManager;
    private String currentFragmentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize JUtils & JActivityManager
        JUtils.initialize(getApplication());
        JUtils.setDebug(BuildConfig.DEBUG, "inMain");
        JActivityManager.getInstance().pushActivity(MainActivity.this);
        // Initialize Layout
        // Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar_inMain);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Drawer
        drawerLayout = NavManager.getInstance().init(MainActivity.this);
        // Fragment
        setFragment(new HomeFragment());
    }

    public void setFragment(Fragment fragment) {
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_container, fragment).commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                // 使用返回键切回首页
                if (!currentFragmentName.equals("HomeFragment")) {
                    setFragment(new HomeFragment());
                    return true;
                } else {
                    return super.onKeyDown(keyCode, event);
                }
        }
        return super.onKeyDown(keyCode, event);
    }

    public void setCurrentFragmentName(String currentFragmentName) {
        this.currentFragmentName = currentFragmentName;
    }

    public String getCurrentFragmentName() {
        return currentFragmentName;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case GetAlbumPicture.SELECT_PIC_FOR_FACE:
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    if (null != bitmap) {
                        CurrentSelectImageBitmap.storage(bitmap);
                        setFragment(new ModifyFaceFragment());
                    }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JActivityManager.getInstance().popActivity(MainActivity.this);
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}
