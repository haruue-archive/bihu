package cn.com.caoyue.bihu.ui.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.jude.utils.JUtils;

import cn.com.caoyue.bihu.R;
import cn.com.caoyue.bihu.data.network.ApiKeys;
import cn.com.caoyue.bihu.data.network.RestApi;
import cn.com.caoyue.bihu.data.storage.CurrentQuestion;
import cn.com.caoyue.bihu.data.storage.CurrentUser;
import cn.com.caoyue.bihu.data.transfer.InformationTransfer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewAnswerDialog extends DialogFragment {

    private EditText contentEditText;

    // implements this interface if you want to create the dialog
    public interface NewAnswerDialogCreater {
        NewAnswerDialogListener getNewAnswerDialogListener();
    }

    public interface NewAnswerDialogListener {

        void onSuccess();
        void onCancel();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setTitle(R.string.new_answer);
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        final View dialogView = inflater.inflate(R.layout.dialog_new_answer, container);
        ((TextView) dialogView.findViewById(R.id.question_title)).setText(CurrentQuestion.getInstance().title);
        contentEditText = ((TextInputLayout) dialogView.findViewById(R.id.input_answer)).getEditText();
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
        String content = contentEditText.getText().toString();
        if (content.isEmpty()) {
            JUtils.Toast(getResources().getString(R.string.answer_content_cannot_be_empty));
            return;
        }
        Call<InformationTransfer> commitAnswerCall = RestApi.getHaruueKnowWebApiService().answer(ApiKeys.HARUUE_KNOW_WEB_APIKEY, CurrentUser.getInstance().token, CurrentQuestion.getInstance().id, content);
        commitAnswerCall.enqueue(new Callback<InformationTransfer>() {
            @Override
            public void onResponse(Response<InformationTransfer> response) {
                if (response.code() == 200) {
                    JUtils.Toast(getResources().getString(R.string.commit_answer_success));
                    getDialog().dismiss();
                    ((NewAnswerDialogCreater) getActivity()).getNewAnswerDialogListener().onSuccess();
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
        outState.putString("content", contentEditText.getText().toString());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (null != savedInstanceState) {
            contentEditText.setText(savedInstanceState.getString("content"));
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        ((NewAnswerDialogCreater) getActivity()).getNewAnswerDialogListener().onCancel();
    }
}
