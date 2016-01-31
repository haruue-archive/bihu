package cn.com.caoyue.bihu.ui.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.jude.utils.JUtils;

import cn.com.caoyue.bihu.R;
import cn.com.caoyue.bihu.data.network.ApiKeys;
import cn.com.caoyue.bihu.data.network.RestApi;
import cn.com.caoyue.bihu.data.transfer.InformationTransfer;
import cn.com.caoyue.bihu.data.transfer.UserTransfer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginDialog extends DialogFragment {

    private String username;
    private String password;

    private UserTransfer userTransfer;

    // implements this interface if you want to create the dialog
    public interface LoginDialogCreater {
        LoginDialogListener getLoginDialogListener();
    }

    public interface LoginDialogListener {
        void onLoginSuccess(UserTransfer userTransfer);

        void onRegisterSuccess(String username, String password);

        void onDialogCancel();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setTitle(getResources().getString(R.string.login_or_register));
        getDialog().setCanceledOnTouchOutside(false);
        final View dialogView = inflater.inflate(R.layout.dialog_login, container);
        final EditText usernameEditText = ((TextInputLayout) dialogView.findViewById(R.id.input_username)).getEditText();
        usernameEditText.setText(getTag());
        final EditText passwordEditText = ((TextInputLayout) dialogView.findViewById(R.id.input_password)).getEditText();
        // Set button listener
        dialogView.findViewById(R.id.button_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameEditText.getText().toString();
                password = passwordEditText.getText().toString();
                if (username.isEmpty() || password.isEmpty()) {
                    JUtils.Toast(getResources().getString(R.string.username_or_password_empty_error));
                    return;
                }
                onLoginDialogLogin(username, password);
            }
        });
        dialogView.findViewById(R.id.button_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameEditText.getText().toString();
                password = passwordEditText.getText().toString();
                if (username.isEmpty() || password.isEmpty()) {
                    JUtils.Toast(getResources().getString(R.string.username_or_password_empty_error));
                    return;
                }
                onLoginDialogRegister(username, password);
            }
        });
        return dialogView;
    }

    public void onLoginDialogLogin(String username, String password) {
        Call<UserTransfer> loginCall = RestApi.getApiService().login(ApiKeys.HARUUE_KNOW_WEB_APIKEY, username, password);
        loginCall.enqueue(new Callback<UserTransfer>() {
            @Override
            public void onResponse(Response<UserTransfer> response) {
                switch (response.code()) {
                    case 200:
                        if (null != response.body()) {
                            JUtils.Toast(getResources().getString(R.string.login_success));
                            userTransfer = response.body();
                            LoginDialog.this.dismiss();
                            ((LoginDialogCreater) getActivity()).getLoginDialogListener().onLoginSuccess(userTransfer);
                        } else {
                            JUtils.Toast(getResources().getString(R.string.login_failed_for_password_error));
                        }
                        break;
                    case 400:
                        JUtils.Toast(getResources().getString(R.string.login_failed_for_password_error));
                        break;
                    case 404:
                        JUtils.ToastLong(getResources().getString(R.string.error_404_apikey));
                        break;
                    default:
                        JUtils.ToastLong(getResources().getString(R.string.other_server_error));
                        JUtils.Log("LoginHTTPCode", response.code() + "");
                        break;
                }
            }

            @Override
            public void onFailure(Throwable t) {
                JUtils.Toast(getResources().getString(R.string.network_error));
            }
        });
    }

    public void onLoginDialogRegister(final String username, final String password) {
        Call<InformationTransfer> loginCall = RestApi.getApiService().register(ApiKeys.HARUUE_KNOW_WEB_APIKEY, username, password);
        loginCall.enqueue(new Callback<InformationTransfer>() {
            @Override
            public void onResponse(Response<InformationTransfer> response) {
                switch (response.code()) {
                    case 200:
                        if (null != response.body()) {
                            JUtils.Toast(getResources().getString(R.string.register_success));
                            ((LoginDialogCreater) getActivity()).getLoginDialogListener().onRegisterSuccess(username, password);
                        } else {
                            JUtils.Toast(getResources().getString(R.string.register_failed_for_username_exist));
                        }
                        break;
                    case 400:
                        JUtils.Toast(getResources().getString(R.string.register_failed_for_username_exist));
                        break;
                    case 404:
                        JUtils.ToastLong(getResources().getString(R.string.error_404_apikey));
                        break;
                    default:
                        JUtils.ToastLong(getResources().getString(R.string.other_server_error));
                        JUtils.Log("RegisterHTTPCode", response.code() + "");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                JUtils.Toast(getResources().getString(R.string.network_error));
            }
        });
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        ((LoginDialogCreater) getActivity()).getLoginDialogListener().onDialogCancel();
    }
}
