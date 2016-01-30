package cn.com.caoyue.bihu.data.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.*;

@Table(name = "User")
public class UserTable extends Model {

    @Column(name = "uid")
    public String id;
    @Column(name = "name")
    public String name;
    @Column(name = "face")
    public String face;
    @Column(name = "token")
    public String token;

    public UserTable() {
        super();
    }

    public UserTable(String id, String name, String face, String token) {
        super();
        this.id = id;
        this.name = name;
        this.face = face;
        this.token = token;
    }
}
