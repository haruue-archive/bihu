package cn.com.caoyue.bihu.data.storage;


import cn.com.caoyue.bihu.data.database.UserTable;
import cn.com.caoyue.bihu.data.transfer.UserTransfer;

public class CurrentUser {

    public String name;
    public String token;
    public String face;
    private static CurrentUser currentUser;

    public static CurrentUser getInstance() {
        if (null == currentUser) {
            currentUser = new CurrentUser();
        }
        return currentUser;
    }

    public void storage(UserTransfer userTransfer) {
        name = userTransfer.name;
        token = userTransfer.token;
        face = userTransfer.face;
    }

    public void storage(UserTable userTable) {
        name = userTable.name;
        token = userTable.name;
        face = userTable.face;
    }

    public static void clean() {
        currentUser = null;
    }

}
