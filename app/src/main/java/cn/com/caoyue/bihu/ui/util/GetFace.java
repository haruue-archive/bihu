package cn.com.caoyue.bihu.ui.util;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import cn.com.caoyue.bihu.R;

public class GetFace {

    private ImageView imageView;
    private String imageUrl;

    public GetFace(ImageView imageView, @Nullable String imageUrl) {
        this.imageView = imageView;
        this.imageUrl = imageUrl;
    }

    public void load() {
        if (null == imageUrl || imageUrl.isEmpty()) {
            return;
        }
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.default_face)
                .showImageOnFail(R.drawable.default_face)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        ImageLoader.getInstance().displayImage(imageUrl, imageView, options);
    }
}
