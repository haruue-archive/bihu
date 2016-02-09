package cn.com.caoyue.bihu.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import cn.com.caoyue.bihu.R;
import cn.com.caoyue.bihu.data.transfer.QuestionTransfer;

public class QuestionAdapter extends RecyclerArrayAdapter<QuestionTransfer> {

    public QuestionAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new QuestionViewHolder(parent);
    }

    public class QuestionViewHolder extends BaseViewHolder<QuestionTransfer> {

        private TextView title;
        private TextView content;
        private TextView stamp;

        public QuestionViewHolder(ViewGroup itemView) {
            super(itemView, R.layout.item_question);
            title = $(R.id.question_title);
            content = $(R.id.question_content);
            stamp = $(R.id.post_stamp);
        }

        @Override
        public void setData(QuestionTransfer data) {
            title.setText(data.title);
            content.setText(data.content);
            stamp.setText(String.format(getContext().getResources().getString(R.string.post_stamp), data.authorName, data.date));
        }
    }

}
