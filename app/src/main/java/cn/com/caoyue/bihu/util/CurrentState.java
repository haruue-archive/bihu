package cn.com.caoyue.bihu.util;

import android.os.Bundle;

import cn.com.caoyue.bihu.data.provider.AnswerListProvider;
import cn.com.caoyue.bihu.data.provider.QuestionListProvider;
import cn.com.caoyue.bihu.data.storage.CurrentFragment;
import cn.com.caoyue.bihu.data.storage.CurrentQuestion;
import cn.com.caoyue.bihu.data.storage.CurrentUser;

/**
 * 存储和恢复 data.storage 包中数据
 */
public class CurrentState {

    public static void save(Bundle outState) {
        outState.putSerializable("CurrentFragment", CurrentFragment.getInstance());
        outState.putSerializable("CurrentUser", CurrentUser.getInstance());
        outState.putSerializable("CurrentQuestion", CurrentQuestion.getInstance());
    }

    public static void restore(Bundle savedInstanceState) {
        CurrentFragment.restoreInstance(savedInstanceState.getSerializable("CurrentFragment"));
        CurrentUser.restoreInstance(savedInstanceState.getSerializable("CurrentUser"));
        CurrentQuestion.restoreInstance(savedInstanceState.getSerializable("CurrentQuestion"));
    }

}
