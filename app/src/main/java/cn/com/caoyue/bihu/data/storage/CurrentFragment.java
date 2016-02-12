package cn.com.caoyue.bihu.data.storage;

import android.support.v4.app.Fragment;

public class CurrentFragment {

    private Fragment fragment;
    private static CurrentFragment currentFragment;

    public static CurrentFragment getInstance() {
        if (null == currentFragment) {
            currentFragment = new CurrentFragment();
        }
        return currentFragment;
    }

    public void storage(Fragment fragment) {
        this.fragment = fragment;
    }

    public Fragment get() {
        return fragment;
    }

    public static void clean() {
        currentFragment = null;
    }

}
