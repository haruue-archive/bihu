package cn.com.caoyue.bihu.ui.navigation;

import android.content.Context;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import com.activeandroid.query.Delete;
import com.jude.utils.JActivityManager;
import com.jude.utils.JUtils;

import cn.com.caoyue.bihu.R;
import cn.com.caoyue.bihu.data.database.UserTable;
import cn.com.caoyue.bihu.data.storage.CurrentUser;
import cn.com.caoyue.bihu.ui.activity.LaunchPageActivity;
import cn.com.caoyue.bihu.ui.activity.MainActivity;
import cn.com.caoyue.bihu.ui.fragment.HomeFragment;
import cn.com.caoyue.bihu.ui.fragment.ModifyFaceFragment;

public class NavListener implements NavigationView.OnNavigationItemSelectedListener {

    Context context;
    DrawerLayout drawerLayout;

    public NavListener(Context context, DrawerLayout drawerLayout) {
        this.context = context;
        this.drawerLayout = drawerLayout;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        String itemTitle = item.getTitle().toString();
        if (itemTitle.equals(context.getResources().getString(R.string.logout))) {
            new Delete().from(UserTable.class).execute();
            CurrentUser.clean();
            JActivityManager.getInstance().closeAllActivity();
            LaunchPageActivity.actionStart(context);
        }
        if (itemTitle.equals(context.getResources().getString(R.string.change_face))) {
            item.setChecked(true);
            ((MainActivity) context).setFragment(new ModifyFaceFragment());
        }
        if (itemTitle.equals(context.getResources().getString(R.string.home))) {
            item.setChecked(true);
            if (!((MainActivity) context).getCurrentFragmentName().equals("HomeFragment")) {
                ((MainActivity) context).setFragment(new HomeFragment());
            }
        }
        drawerLayout.closeDrawers();
        return true;
    }
}
