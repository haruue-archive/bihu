package cn.com.caoyue.bihu.ui.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.ArrayList;

import cn.com.caoyue.bihu.R;
import cn.com.caoyue.bihu.data.provider.QuestionListProvider;
import cn.com.caoyue.bihu.data.storage.CurrentQuestion;
import cn.com.caoyue.bihu.data.transfer.QuestionTransfer;
import cn.com.caoyue.bihu.ui.activity.MainActivity;
import cn.com.caoyue.bihu.ui.adapter.QuestionAdapter;

public class HomeFragment extends Fragment implements QuestionListProvider.QuestionListDemander {

    View view;
    private EasyRecyclerView recyclerView;
    private QuestionAdapter adapter;

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
        adapter.addAll(QuestionListProvider.getInstance(HomeFragment.this).getArray());
        adapter.notifyDataSetChanged();
        if (CurrentQuestion.isStoraged()) {
            recyclerView.scrollToPosition(CurrentQuestion.getInstance().position);
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
        adapter.clear();
        adapter.addAll(QuestionListProvider.getInstance(HomeFragment.this).getArray());
        adapter.notifyDataSetChanged();
        if (CurrentQuestion.isStoraged()) {
            recyclerView.scrollToPosition(CurrentQuestion.getInstance().position);
        }
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
            ((MainActivity) getActivity()).setFragment(new AnswerFragment(), AnswerFragment.class.getName() + CurrentQuestion.getInstance().id);
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
            adapter.clear();
            QuestionListProvider.getInstance(HomeFragment.this).refresh();
        }
    }

}
