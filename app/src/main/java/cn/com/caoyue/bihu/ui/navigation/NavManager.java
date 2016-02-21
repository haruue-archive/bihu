package cn.com.caoyue.bihu.ui.navigation;

import android.content.Context;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import cn.com.caoyue.bihu.R;
import cn.com.caoyue.bihu.data.storage.CurrentUser;
import cn.com.caoyue.bihu.ui.util.GetFace;
import de.hdodenhof.circleimageview.CircleImageView;

public class NavManager {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    CircleImageView faceView;
    Context context;
    private static NavManager navManager;

    public static NavManager getInstance() {
        if (null == navManager) {
            navManager = new NavManager();
        }
        return navManager;
    }

    public DrawerLayout init(Context context) {
        this.context = context;
        drawerLayout = (DrawerLayout) ((AppCompatActivity) context).findViewById(R.id.drawer_inMain);
        drawerLayout.setDrawerShadow(R.drawable.shadow, GravityCompat.START);
        navigationView = (NavigationView) ((AppCompatActivity) context).findViewById(R.id.nav_view_inMain);
        navigationView.setCheckedItem(R.id.home);
        navigationView.setNavigationItemSelectedListener(new NavListener(context, drawerLayout));
        // Set Header
        ((TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_username)).setText(CurrentUser.getInstance().name);
        // Set Face
        faceView = (CircleImageView) navigationView.getHeaderView(0).findViewById(R.id.nav_face);
        new GetFace(faceView, CurrentUser.getInstance().face).load();
        return drawerLayout;
    }

    public DrawerLayout refreshFace() {
        new GetFace(faceView, CurrentUser.getInstance().face).load();
        return drawerLayout;
    }

}
