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
import android.widget.TextView;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.ArrayList;

import cn.com.caoyue.bihu.R;
import cn.com.caoyue.bihu.data.provider.AnswerListProvider;
import cn.com.caoyue.bihu.data.storage.CurrentQuestion;
import cn.com.caoyue.bihu.data.transfer.AnswerTransfer;
import cn.com.caoyue.bihu.ui.activity.MainActivity;
import cn.com.caoyue.bihu.ui.adapter.AnswerAdapter;

public class AnswerFragment extends Fragment implements AnswerListProvider.AnswerListDemander {

    View view;
    EasyRecyclerView recyclerView;
    AnswerAdapter adapter;
    int currentPosition = -1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_answer, container, false);
        // Activity
        ((MainActivity) getActivity()).setCurrentFragmentName("AnswerFragment");
        // RecyclerView
        recyclerView = (EasyRecyclerView) view.findViewById(R.id.recycler_view_answer);
        recyclerView.setAdapter(adapter = new AnswerAdapter(getActivity()));
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
        // Question Header
        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                View header = getLayoutInflater(savedInstanceState).inflate(R.layout.header_question, parent, false);
                ((TextView) header.findViewById(R.id.question_author_name)).setText(CurrentQuestion.getInstance().authorName);
                ((TextView) header.findViewById(R.id.question_date)).setText(CurrentQuestion.getInstance().date);
                ((TextView) header.findViewById(R.id.question_title)).setText(CurrentQuestion.getInstance().title);
                ((TextView) header.findViewById(R.id.question_content)).setText(CurrentQuestion.getInstance().content);
                return header;
            }

            @Override
            public void onBindView(View headerView) {

            }
        });
        new Listener().onRefresh();
        return view;
    }

    @Override
    public AnswerListProvider.AnswerListLoadListener getAnswerListLoadListener() {
        return new AnswerListProvider.AnswerListLoadListener() {
            @Override
            public void onSuccess(ArrayList<AnswerTransfer> diff) {
                adapter.addAll(diff);
                adapter.notifyDataSetChanged();
                recyclerView.showRecycler();
            }

            @Override
            public void onEmpty(int onPage) {
                adapter.stopMore();
                if (onPage == 0) {
                    recyclerView.showRecycler();
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
        adapter.addAll(AnswerListProvider.getInstance(AnswerFragment.this).getArray());
        adapter.notifyDataSetChanged();
        if (currentPosition != -1) {
            recyclerView.scrollToPosition(currentPosition);
        }
        recyclerView.showRecycler();
    }

    private class Listener implements SwipeRefreshLayout.OnRefreshListener, RecyclerArrayAdapter.OnLoadMoreListener, RecyclerArrayAdapter.OnItemClickListener {

        @Override
        public void onRefresh() {
            adapter.clear();
            AnswerListProvider.getInstance(AnswerFragment.this).refresh();
        }

        @Override
        public void onItemClick(int position) {
            rippleAnimation(position);
        }

        @Override
        public void onLoadMore() {
            AnswerListProvider.getInstance(AnswerFragment.this).loadNextPage();
        }

        // Ripple 动画
        void rippleAnimation(int position) {
            final View view = AnswerFragment.this.view.findViewById((int) adapter.getItemId(position - 1));
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
    }
}
