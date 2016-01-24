package cn.com.caoyue.bihu.data.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "PictureCache")
public class PictureCacheTable extends Model {

    @Column(name = "url")
    public String url;

    @Column(name = "path")
    public String path;

    public PictureCacheTable() {

    }

    public PictureCacheTable(String url, String path) {
        this.url = url;
        this.path = path;
    }
}
