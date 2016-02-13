package cn.com.caoyue.bihu.data.storage;


import com.jude.utils.JUtils;

import java.io.Serializable;

import cn.com.caoyue.bihu.data.database.UserTable;
import cn.com.caoyue.bihu.data.transfer.UserTransfer;

public class CurrentUser implements Serializable {

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

    public static void restoreInstance(Serializable data) {
        try {
            currentUser = (CurrentUser) data;
        } catch (Exception e) {
            JUtils.Log("inRestoreInstance_CurrentUser, " + "Failed for " + e.toString());
            e.printStackTrace();
        }
    }

    public static void clean() {
        currentUser = null;
    }

}
