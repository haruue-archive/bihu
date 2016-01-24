package cn.com.caoyue.bihu.util;

public class Picture {
    private String url;
    private String path;

    public Picture(String url) {

    }

    public String getUrl() {
        return url;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return getPath();
    }
}
