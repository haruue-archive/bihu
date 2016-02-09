package cn.com.caoyue.bihu.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class AnswerFragment extends Fragment {

    private int questionId;

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        questionId = args.getInt("questionId", -1);
    }
}
