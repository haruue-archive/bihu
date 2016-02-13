package cn.com.caoyue.bihu.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * 从系统相册中取出图片
 */
public class GetAlbumPicture {

    Context context;
    public static final int SELECT_PIC_FOR_FACE = 86;

    private int requestCode;


    public GetAlbumPicture(Context context, int requestCode) {
        this.context = context;
        this.requestCode = requestCode;
    }

    public void startSelect() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 80);
        intent.putExtra("outputY", 80);
        intent.putExtra("return-data", true);
        ((Activity) context).startActivityForResult(intent, requestCode);
    }
}
