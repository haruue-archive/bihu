package cn.com.caoyue.bihu.data.convert;

import cn.com.caoyue.bihu.data.database.UserTable;
import cn.com.caoyue.bihu.data.transfer.UserTransfer;

public class UserConverter {

    public String id;
    public String name;
    public String face;
    public String password;
    public String token;

    public UserConverter(String id, String name, String face, String password, String token) {
        this.id = id;
        this.name = name;
        this.face = face;
        this.password = password;
        this.token = token;
    }

    public UserConverter(UserTransfer userTransfer) {
        this(userTransfer.id, userTransfer.name, userTransfer.face, userTransfer.password, userTransfer.token);
    }

    public UserConverter(UserTable userTable) {
        this(userTable.id, userTable.name, userTable.face, "", userTable.token);
    }

    public UserTransfer toUserTransfer() {
        UserTransfer userTransfer = new UserTransfer();
        userTransfer.id = id;
        userTransfer.name = name;
        userTransfer.token = token;
        userTransfer.face = face;
        userTransfer.password = password;
        return userTransfer;
    }

    public UserTable toUserTable() {
        return new UserTable(id, name, face, token);
    }
}
