package cn.com.caoyue.bihu.ui.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.jude.utils.JUtils;

import cn.com.caoyue.bihu.R;
import cn.com.caoyue.bihu.data.network.ApiKeys;
import cn.com.caoyue.bihu.data.network.RestApi;
import cn.com.caoyue.bihu.data.storage.CurrentUser;
import cn.com.caoyue.bihu.data.transfer.InformationTransfer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewQuestionDialog extends DialogFragment {

    private EditText titleEditText;
    private EditText contentEditText;

    // implements this interface if you want to create the dialog
    public interface NewQuestionDialogCreater {
        NewQuestionDialogListener getNewQuestionDialogListener();
    }

    public interface NewQuestionDialogListener {

        void onSuccess();
        void onCancel();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setTitle(R.string.new_question);
        getDialog().setCanceledOnTouchOutside(false);
        final View dialogView = inflater.inflate(R.layout.dialog_new_question, container);
        titleEditText = ((TextInputLayout) dialogView.findViewById(R.id.input_question_title)).getEditText();
        contentEditText = ((TextInputLayout) dialogView.findViewById(R.id.input_question_content)).getEditText();
        onViewStateRestored(savedInstanceState);
        // Button
        dialogView.findViewById(R.id.button_give_up).setOnClickListener(new Listener());
        dialogView.findViewById(R.id.button_commit).setOnClickListener(new Listener());
        return dialogView;
    }

    public class Listener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_give_up:
                    getDialog().dismiss();
                    break;
                case R.id.button_commit:
                    onCommit();
                    break;
            }

        }
    }

    private void onCommit() {
        String title = titleEditText.getText().toString();
        String content = contentEditText.getText().toString();
        if (title.isEmpty()) {
            JUtils.Toast(getResources().getString(R.string.title_cannot_be_empty));
            return;
        }
        Call<InformationTransfer> commitQuestionCall = RestApi.getHaruueKnowWebApiService().question(ApiKeys.HARUUE_KNOW_WEB_APIKEY, CurrentUser.getInstance().token, title, content);
        commitQuestionCall.enqueue(new Callback<InformationTransfer>() {
            @Override
            public void onResponse(Response<InformationTransfer> response) {
                if (response.code() == 200) {
                    JUtils.Toast(getResources().getString(R.string.commit_question_success));
                    getDialog().dismiss();
                    ((NewQuestionDialogCreater) getActivity()).getNewQuestionDialogListener().onSuccess();
                } else {
                    JUtils.Toast(getResources().getString(R.string.fail_please_retry));
                }
            }

            @Override
            public void onFailure(Throwable t) {
                JUtils.Toast(getResources().getString(R.string.network_error));
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("title", titleEditText.getText().toString());
        outState.putString("content", contentEditText.getText().toString());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (null != savedInstanceState) {
            titleEditText.setText(savedInstanceState.getString("title"));
            contentEditText.setText(savedInstanceState.getString("content"));
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        ((NewQuestionDialogCreater) getActivity()).getNewQuestionDialogListener().onCancel();
    }
}
