package cn.com.caoyue.bihu.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.jude.utils.JActivityManager;
import com.jude.utils.JUtils;

import cn.com.caoyue.bihu.BuildConfig;
import cn.com.caoyue.bihu.R;
import cn.com.caoyue.bihu.data.convert.UserConverter;
import cn.com.caoyue.bihu.data.database.UserTable;
import cn.com.caoyue.bihu.data.network.ApiKeys;
import cn.com.caoyue.bihu.data.network.RestApi;
import cn.com.caoyue.bihu.data.storage.CurrentUser;
import cn.com.caoyue.bihu.data.transfer.UserTransfer;
import cn.com.caoyue.bihu.ui.dialog.LoginDialog;
import cn.com.caoyue.bihu.util.AppControl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LaunchPageActivity extends AppCompatActivity implements LoginDialog.LoginDialogCreater {

    Handler handler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize JUtils & JActivityManager
        JUtils.initialize(getApplication());
        JUtils.setDebug(BuildConfig.DEBUG, "inMain");
        JActivityManager.getInstance().pushActivity(LaunchPageActivity.this);
        setContentView(R.layout.activity_launch_page);
        // Button
        findViewById(R.id.button_show_login_dialog).setOnClickListener(new Listener());
        // Check login or not
        checkLogin();
    }

    private void checkLogin() {
        UserTable userTable = (new Select())
                .from(UserTable.class)
                .orderBy("RANDOM()")
                .executeSingle();
        if (null == userTable) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    showLoginDialog();
                }
            }, 500);
            return;
        }
        Call<UserTransfer> loginWithOldTokenCall = RestApi.getHaruueKnowWebApiService().loginWithOldToken(ApiKeys.HARUUE_KNOW_WEB_APIKEY, userTable.token);
        loginWithOldTokenCall.enqueue(new Callback<UserTransfer>() {
            @Override
            public void onResponse(Response<UserTransfer> response) {
                switch (response.code()) {
                    case 200:
                        if (null != response.body()) {
                            onLoginSuccess(response.body());
                        } else {
                            JUtils.Toast(getResources().getString(R.string.login_with_old_token_failed));
                            showLoginDialog();
                        }
                        break;
                    case 404:
                        JUtils.Toast(getResources().getString(R.string.error_404_apikey));
                        AppControl.exitApp();
                        break;
                    default:
                        JUtils.Toast(getResources().getString(R.string.login_with_old_token_failed));
                        showLoginDialog();
                        break;
                }
            }

            @Override
            public void onFailure(Throwable t) {
                JUtils.Toast(getResources().getString(R.string.network_error));
            }
        });
    }

    private void showLoginDialog(String defaultUsername) {
        LoginDialog loginDialog = new LoginDialog();
        loginDialog.show(getSupportFragmentManager(), defaultUsername);
    }

    private void showLoginDialog() {
        showLoginDialog("");
    }

    @Override
    public LoginDialog.LoginDialogListener getLoginDialogListener() {
        return new LoginDialog.LoginDialogListener() {
            @Override
            public void onLoginSuccess(UserTransfer userTransfer) {
                LaunchPageActivity.this.onLoginSuccess(userTransfer);
            }

            @Override
            public void onRegisterSuccess(String username, String password) {

            }

            @Override
            public void onDialogCancel() {

            }
        };
    }

    public void onLoginSuccess(UserTransfer userTransfer) {
        CurrentUser.getInstance().storage(userTransfer);
        // 数据库管理
        new Delete().from(UserTable.class).execute();
        new UserConverter(userTransfer).toUserTable().save();
        // 启动主活动
        findViewById(R.id.button_show_login_dialog).setVisibility(View.GONE);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                JActivityManager.getInstance().closeAllActivity();
                MainActivity.actionStart(LaunchPageActivity.this);
            }
        }, 1500);
    }

    private class Listener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_show_login_dialog:
                    checkLogin();
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JActivityManager.getInstance().popActivity(LaunchPageActivity.this);
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, LaunchPageActivity.class);
        ((AppCompatActivity) context).overridePendingTransition(android.R.anim.fade_out, android.R.anim.fade_in);
        context.startActivity(intent);
    }
}
