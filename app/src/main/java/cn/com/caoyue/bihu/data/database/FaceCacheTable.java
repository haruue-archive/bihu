package cn.com.caoyue.bihu.data.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.*;

@Table(name = "FaceCache")
public class FaceCacheTable extends Model {

    @Column(name = "url")
    public String url;

    @Column(name = "path")
    public String path;

    public FaceCacheTable() {

    }

    public FaceCacheTable(String url, String path) {
        this.url = url;
        this.path = path;
    }
}
