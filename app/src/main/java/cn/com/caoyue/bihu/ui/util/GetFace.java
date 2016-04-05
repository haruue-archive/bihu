package cn.com.caoyue.bihu.ui.util;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import cn.com.caoyue.imageloader.ImageLoader;

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
        ImageLoader.getInstance().loadImage(imageUrl, imageView);
    }
}
