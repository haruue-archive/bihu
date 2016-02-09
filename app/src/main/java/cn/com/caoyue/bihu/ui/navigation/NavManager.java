package cn.com.caoyue.bihu.ui.navigation;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import cn.com.caoyue.bihu.R;
import cn.com.caoyue.bihu.data.storage.CurrentUser;

public class NavManager {

    DrawerLayout drawerLayout;
    NavigationView navigationView;

    public static NavManager getInstance() {
        return new NavManager();
    }

    public DrawerLayout init(Context context) {
        drawerLayout = (DrawerLayout) ((AppCompatActivity) context).findViewById(R.id.drawer_inMain);
        drawerLayout.setDrawerShadow(R.drawable.shadow, GravityCompat.START);
        navigationView = (NavigationView) ((AppCompatActivity) context).findViewById(R.id.nav_view_inMain);
        navigationView.setCheckedItem(R.id.home);
        navigationView.setNavigationItemSelectedListener(new NavListener(context, drawerLayout));
        // Set Header
        ((TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_username)).setText(CurrentUser.getInstance().name);
        return drawerLayout;
    }
}
