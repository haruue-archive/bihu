package cn.com.caoyue.bihu.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jude.utils.JUtils;

import cn.com.caoyue.bihu.BuildConfig;
import cn.com.caoyue.bihu.R;
import cn.com.caoyue.bihu.data.network.ApiKeys;
import cn.com.caoyue.bihu.data.network.RestApi;
import cn.com.caoyue.bihu.data.transfer.InformationTransfer;
import cn.com.caoyue.bihu.data.transfer.UserTransfer;
import cn.com.caoyue.bihu.ui.dialog.LoginDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LaunchPageActivity extends AppCompatActivity implements LoginDialog.LoginDialogListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JUtils.initialize(getApplication());
        JUtils.setDebug(BuildConfig.DEBUG, "inMain");;
        setContentView(R.layout.activity_launch_page);
        showLoginDialog();
    }

    private void showLoginDialog(String defaultUsername) {
        LoginDialog loginDialog = new LoginDialog();
        loginDialog.show(getSupportFragmentManager(), defaultUsername);
    }

    private void showLoginDialog() {
        showLoginDialog("");
    }

    @Override
    public boolean onLoginDialogLogin(String username, String password) {
        return login(username, password);
    }

    @Override
    public boolean onLoginDialogRegister(String username, String password) {
        return register(username, password);
    }

    @Override
    public boolean onLoginDialogCancel() {
        return true;
    }

    private boolean login(String username, String password) {
        final boolean[] status = new boolean[1];
        Call<UserTransfer> loginCall = RestApi.getApiService().login(ApiKeys.HARUUE_KNOW_WEB_APIKEY, username, password);
        loginCall.enqueue(new Callback<UserTransfer>() {
            @Override
            public void onResponse(Response<UserTransfer> response) {
                switch (response.code()) {
                    case 200:
                        if (null != response.body()) {
                            JUtils.Toast(getResources().getString(R.string.login_success));
                            //TODO: add login success code here
                            status[0] = true;
                        } else {
                            JUtils.Toast(getResources().getString(R.string.login_failed_for_password_error));
                            status[0] = false;
                        }
                        break;
                    case 404:
                        JUtils.ToastLong(getResources().getString(R.string.error_404_apikey));
                        status[0] = false;
                        break;
                    default:
                        JUtils.ToastLong(getResources().getString(R.string.other_server_error));
                        status[0] = false;
                        break;
                }
            }

            @Override
            public void onFailure(Throwable t) {
                JUtils.Toast(getResources().getString(R.string.network_error));
                status[0] = false;
            }
        });
        return status[0];
    }

    private boolean register(String username, String password) {
        final boolean[] status = new boolean[1];
        Call<InformationTransfer> loginCall = RestApi.getApiService().register(ApiKeys.HARUUE_KNOW_WEB_APIKEY, username, password);
        loginCall.enqueue(new Callback<InformationTransfer>() {
            @Override
            public void onResponse(Response<InformationTransfer> response) {
                switch (response.code()) {
                    case 200:
                        if (null != response.body()) {
                            JUtils.Toast(getResources().getString(R.string.register_success));
                            //TODO: add register success code here
                            status[0] = true;
                        } else {
                            JUtils.Toast(getResources().getString(R.string.register_failed_for_username_exist));
                            status[0] = false;
                        }
                        break;
                    case 400:
                        JUtils.Toast(getResources().getString(R.string.register_failed_for_username_exist));
                        status[0] = false;
                        break;
                    case 404:
                        JUtils.ToastLong(getResources().getString(R.string.error_404_apikey));
                        status[0] = false;
                        break;
                    default:
                        JUtils.ToastLong(getResources().getString(R.string.other_server_error));
                        status[0] = false;
                }
            }

            @Override
            public void onFailure(Throwable t) {
                JUtils.Toast(getResources().getString(R.string.network_error));
                status[0] = false;
            }
        });
        return status[0];
    }
}
