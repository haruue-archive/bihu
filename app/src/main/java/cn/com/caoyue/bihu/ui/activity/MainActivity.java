package cn.com.caoyue.bihu.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.activeandroid.ActiveAndroid;
import com.jude.utils.JActivityManager;
import com.jude.utils.JUtils;

import cn.com.caoyue.bihu.BuildConfig;
import cn.com.caoyue.bihu.R;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

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
        toolbar = (Toolbar) findViewById(R.id.toolbar_inMainActivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Drawer
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_inMain);
        navigationView = (NavigationView) findViewById(R.id.nav_view_inMain);
        navigationView.setCheckedItem(R.id.home);
        navigationView.setNavigationItemSelectedListener(new Listeners());
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

    private class Listeners implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

        @Override
        public void onClick(View v) {

        }

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            JUtils.Toast(item.getTitle().toString());
            item.setChecked(true);
            drawerLayout.closeDrawers();
            return true;
        }
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        ((AppCompatActivity) context).startActivity(intent);
    }
}
