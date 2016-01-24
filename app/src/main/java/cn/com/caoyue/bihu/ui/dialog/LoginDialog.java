package cn.com.caoyue.bihu.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.iangclifton.android.floatlabel.FloatLabel;

import cn.com.caoyue.bihu.R;

public class LoginDialog extends DialogFragment {

    private String username;
    private String password;

    public interface LoginDialogListener {
        boolean onLoginDialogLogin(String username, String password);
        boolean onLoginDialogRegister(String username, String password);
        boolean onLoginDialogCancel();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setTitle(getResources().getString(R.string.login_or_register));
        final LoginDialogListener listener = (LoginDialogListener) getActivity();
        final View dialogView = inflater.inflate(R.layout.dialog_login, container);
        final EditText usernameEditText = ((FloatLabel) dialogView.findViewById(R.id.input_username)).getEditText();
        usernameEditText.setText(getTag());
        final EditText passwordEditText = ((FloatLabel) dialogView.findViewById(R.id.input_password)).getEditText();
        // Set button listener
        dialogView.findViewById(R.id.button_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameEditText.getText().toString();
                password = passwordEditText.getText().toString();
                boolean status = listener.onLoginDialogLogin(username, password);
                if (status) {
                    getDialog().dismiss();
                }
            }
        });
        dialogView.findViewById(R.id.button_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameEditText.getText().toString();
                password = passwordEditText.getText().toString();
                boolean status = listener.onLoginDialogRegister(username, password);
                if (status) {
                    getDialog().dismiss();
                }
            }
        });
        return dialogView;
    }

}
