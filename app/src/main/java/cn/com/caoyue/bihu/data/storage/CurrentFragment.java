package cn.com.caoyue.bihu.data.storage;

import android.support.v4.app.Fragment;

import com.jude.utils.JUtils;

import java.io.Serializable;

import cn.com.caoyue.bihu.ui.fragment.HomeFragment;

public class CurrentFragment implements Serializable {

    private Class<? extends Fragment> fragment;
    private static CurrentFragment currentFragment;

    public static CurrentFragment getInstance() {
        if (null == currentFragment) {
            currentFragment = new CurrentFragment();
            currentFragment.storage(new HomeFragment());
        }
        return currentFragment;
    }

    public <T extends Fragment> void storage(T fragment) {
        this.fragment = fragment.getClass();
    }

    public static void restoreInstance(Serializable data) {
        try {
            currentFragment = (CurrentFragment) data;
        } catch (Exception e) {
            JUtils.Log("inRestoreInstance_CurrentFragment, " + "Failed for " + e.toString());
            e.printStackTrace();
        }
    }

    public Class getSavedFragmentClass() {
        return fragment;
    }

    public String getName() {
        return fragment.getName();
    }

    public static void clean() {
        currentFragment = null;
    }

}
