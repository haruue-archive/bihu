package cn.com.caoyue.bihu.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import cn.com.caoyue.bihu.R;
import cn.com.caoyue.bihu.data.transfer.AnswerTransfer;
import cn.com.caoyue.bihu.ui.widget.CircleImageView;

public class AnswerAdapter extends RecyclerArrayAdapter<AnswerTransfer> {

    public AnswerAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new AnswerViewHolder(parent);
    }

    public class AnswerViewHolder extends BaseViewHolder<AnswerTransfer> {

        private TextView authorName;
        private TextView content;
        private TextView date;
        private CircleImageView authorFace;

        public AnswerViewHolder(ViewGroup itemView) {
            super(itemView, R.layout.item_answer);
            authorName = $(R.id.answer_author_name);
            content = $(R.id.answer_content);
            date = $(R.id.answer_date);
            authorFace = $(R.id.answer_author_face);
        }

        @Override
        public void setData(AnswerTransfer data) {
            authorName.setText(data.authorName);
            content.setText(data.content);
            date.setText(data.date);
        }
    }
}
