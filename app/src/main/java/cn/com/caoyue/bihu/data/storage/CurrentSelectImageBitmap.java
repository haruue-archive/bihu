package cn.com.caoyue.bihu.data.storage;

import android.graphics.Bitmap;

public class CurrentSelectImageBitmap {
    public static Bitmap bitmap;

    public static void storage(Bitmap data) {
        bitmap = data;
    }

    public static void clean() {
        bitmap = null;
    }

}
