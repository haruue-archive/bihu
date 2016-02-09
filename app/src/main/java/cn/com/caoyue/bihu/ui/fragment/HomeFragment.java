package cn.com.caoyue.bihu.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.utils.JUtils;

import java.util.ArrayList;

import cn.com.caoyue.bihu.R;
import cn.com.caoyue.bihu.data.provider.QuestionListProvider;
import cn.com.caoyue.bihu.data.transfer.QuestionTransfer;
import cn.com.caoyue.bihu.ui.adapter.QuestionAdapter;

public class HomeFragment extends Fragment implements QuestionListProvider.QuestionListDemander {

    private EasyRecyclerView recyclerView;
    private QuestionAdapter adapter;
    private Handler handler = new Handler();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = (EasyRecyclerView) view.findViewById(R.id.recycler_view_question);
        recyclerView.setAdapter(adapter = new QuestionAdapter(getActivity()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.showProgress();
        adapter.setError(R.layout.view_error);
        adapter.setMore(R.layout.view_more, new Listener());
        adapter.setNoMore(R.layout.view_no_more);
        adapter.addAll(QuestionListProvider.getInstance(HomeFragment.this).getArray());
        adapter.notifyDataSetChanged();
        return view;
    }

    @Override
    public QuestionListProvider.QuestionListLoadListener getListener() {
        return new QuestionListProvider.QuestionListLoadListener() {
            @Override
            public void onSuccess(ArrayList<QuestionTransfer> diff) {
                adapter.addAll(diff);
                adapter.notifyDataSetChanged();
                recyclerView.showRecycler();
            }

            @Override
            public void onEmpty() {
                adapter.stopMore();
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
    }

    private class Listener implements RecyclerArrayAdapter.OnLoadMoreListener, RecyclerArrayAdapter.OnItemClickListener {

        @Override
        public void onLoadMore() {
            QuestionListProvider.getInstance(HomeFragment.this).loadNextPage();
        }

        @Override
        public void onItemClick(int position) {

        }

    }

}
