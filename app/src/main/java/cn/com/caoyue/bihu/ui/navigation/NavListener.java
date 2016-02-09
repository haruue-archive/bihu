package cn.com.caoyue.bihu.ui.navigation;

import android.content.Context;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import com.jude.utils.JUtils;

public class NavListener implements NavigationView.OnNavigationItemSelectedListener {

    Context context;
    DrawerLayout drawerLayout;

    public NavListener(Context context, DrawerLayout drawerLayout) {
        this.context = context;
        this.drawerLayout = drawerLayout;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        JUtils.Toast(item.getTitle().toString());
        item.setChecked(true);
        drawerLayout.closeDrawers();
        return true;
    }
}
