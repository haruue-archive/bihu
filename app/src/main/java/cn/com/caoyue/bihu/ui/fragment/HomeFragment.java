package cn.com.caoyue.bihu.ui.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.utils.JUtils;

import java.util.ArrayList;

import cn.com.caoyue.bihu.R;
import cn.com.caoyue.bihu.data.provider.QuestionListProvider;
import cn.com.caoyue.bihu.data.storage.CurrentQuestion;
import cn.com.caoyue.bihu.data.storage.CurrentUser;
import cn.com.caoyue.bihu.data.transfer.QuestionTransfer;
import cn.com.caoyue.bihu.ui.activity.MainActivity;
import cn.com.caoyue.bihu.ui.adapter.QuestionAdapter;

public class HomeFragment extends Fragment implements QuestionListProvider.QuestionListDemander, MainActivity.CommitSuccessCallBack {

    View view;
    private EasyRecyclerView recyclerView;
    private QuestionAdapter adapter;
    private Handler handler = new Handler();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        // RecyclerView
        recyclerView = (EasyRecyclerView) view.findViewById(R.id.recycler_view_question);
        recyclerView.setAdapter(adapter = new QuestionAdapter(getActivity()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setRefreshListener(new Listener());
        recyclerView.showProgress();
        adapter.setError(R.layout.view_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.resumeMore();
            }
        });
        adapter.setMore(R.layout.view_more, new Listener());
        adapter.setNoMore(R.layout.view_no_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.resumeMore();
            }
        });
        adapter.setOnItemClickListener(new Listener());
        // Long Press Menu
        registerForContextMenu(recyclerView);
        adapter.setOnItemLongClickListener(new RecyclerArrayAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemClick(int position) {
                CurrentQuestion.getInstance().storage(adapter.getItem(position), position);
                return false;
            }
        });
        adapter.clear();
        adapter.addAll(QuestionListProvider.getInstance(HomeFragment.this).getArray());
        adapter.notifyDataSetChanged();
        if (CurrentQuestion.isStoraged()) {
            recyclerView.scrollToPosition(CurrentQuestion.getInstance().position - 1);
        }
        return view;
    }

    @Override
    public QuestionListProvider.QuestionListLoadListener getQuestionListLoadListener() {
        return new QuestionListProvider.QuestionListLoadListener() {
            @Override
            public void onSuccess(ArrayList<QuestionTransfer> diff) {
                adapter.addAll(diff);
                adapter.notifyDataSetChanged();
                recyclerView.showRecycler();
            }

            @Override
            public void onEmpty(int onPage) {
                adapter.stopMore();
                if (onPage == 0) {
                    recyclerView.showEmpty();
                }
            }

            @Override
            public void onFailure() {
                adapter.pauseMore();
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        if (CurrentQuestion.isStoraged()) {
            recyclerView.scrollToPosition(CurrentQuestion.getInstance().position - 1);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle(CurrentQuestion.getInstance().title);
        menu.add(0, 0, 0, R.string.copy_question_title);
        menu.add(0, 1, 0, R.string.copy_question_content);
        menu.add(0, 2, 0, R.string.back_to_top);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                JUtils.copyToClipboard(CurrentQuestion.getInstance().title);
                break;
            case 1:
                JUtils.copyToClipboard(CurrentQuestion.getInstance().content);
                break;
            case 2:
                recyclerView.scrollToPosition(0);
        }
        return true;
    }

    @Override
    public void onCommitSuccess() {
        new Listener().onRefresh();
    }

    private class Listener implements RecyclerArrayAdapter.OnLoadMoreListener, RecyclerArrayAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

        @Override
        public void onLoadMore() {
            QuestionListProvider.getInstance(HomeFragment.this).loadNextPage();
        }

        @Override
        public void onItemClick(int position) {
            rippleAnimation(position);
            CurrentQuestion.getInstance().storage(adapter.getItem(position), position);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ((MainActivity) getActivity()).setFragment(new AnswerFragment(), AnswerFragment.class.getName() + CurrentQuestion.getInstance().id);
                }
            }, 500);
        }

        // Ripple 动画
        void rippleAnimation(int position) {
            final View view = HomeFragment.this.view.findViewById((int) adapter.getItemId(position));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                view.animate().translationZ(15F).setDuration(300).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            view.animate().translationZ(1f).setDuration(500).start();
                        }
                    }
                }).start();
            }
        }

        @Override
        public void onRefresh() {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    adapter.clear();
                    QuestionListProvider.getInstance(HomeFragment.this).refresh();
                }
            }, 500);
        }
    }

}
