package cn.com.caoyue.bihu.util;

import com.jude.utils.JActivityManager;

public class AppControl {
    public static void exitApp() {
        JActivityManager.getInstance().closeAllActivity();
        System.exit(0);
    }
}
