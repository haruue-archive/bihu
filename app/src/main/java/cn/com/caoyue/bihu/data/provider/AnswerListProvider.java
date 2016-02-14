package cn.com.caoyue.bihu.data.provider;

import com.jude.utils.JUtils;

import java.util.ArrayList;

import cn.com.caoyue.bihu.data.network.ApiKeys;
import cn.com.caoyue.bihu.data.network.RestApi;
import cn.com.caoyue.bihu.data.storage.CurrentQuestion;
import cn.com.caoyue.bihu.data.transfer.AnswerListTransfer;
import cn.com.caoyue.bihu.data.transfer.AnswerTransfer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnswerListProvider {

    private ArrayList<AnswerTransfer> array;
    private int currentPage;
    private String questionId;
    private AnswerListDemander demander;
    private static AnswerListProvider provider;

    public static AnswerListProvider getInstance(AnswerListDemander demander) {
        if (null == provider) {
            provider = new AnswerListProvider();
            provider.init();
        } else if (!provider.questionId.equals(CurrentQuestion.getInstance().id)) {
            provider = new AnswerListProvider();
            provider.init();
        }
        provider.demander = demander;
        provider.questionId = CurrentQuestion.getInstance().id;
        return provider;
    }

    public ArrayList<AnswerTransfer> getArray() {
        return array;
    }

    private void init() {
        array = new ArrayList<>(0);
        currentPage = 0;
        loadPage(currentPage);
    }

    public void loadPage(final int page) {
        JUtils.Log("inAnswerListProvider_currentPage", currentPage + "");
        JUtils.Log("inAnswerListProvider_toLoad", page + "");
        Call<AnswerListTransfer> getAnswerListCall = RestApi.getHaruueKnowWebApiService().getAnswerList(ApiKeys.HARUUE_KNOW_WEB_APIKEY, questionId, page + "", "false");
        getAnswerListCall.enqueue(new Callback<AnswerListTransfer>() {
            @Override
            public void onResponse(Response<AnswerListTransfer> response) {
                if (response.code() == 200) {
                    if (null == response.body().answers) {
                        demander.getAnswerListLoadListener().onEmpty(page);
                        return;
                    }
                    ArrayList<AnswerTransfer> diff = new ArrayList<>(0);
                    synchronized (this) {
                        for (AnswerTransfer i : response.body().answers) {
                            if (!array.contains(i)) {
                                array.add(i);
                                diff.add(i);
                            }
                        }
                    }
                    currentPage = page;
                    demander.getAnswerListLoadListener().onSuccess(diff);
                } else {
                    demander.getAnswerListLoadListener().onFailure();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                demander.getAnswerListLoadListener().onFailure();
            }
        });
    }

    public void loadNextPage() {
        loadPage(currentPage + 1);
    }

    public synchronized void refresh() {
        currentPage = 0;
        array.clear();
        loadPage(currentPage);
    }

    public static void clean() {
        provider = null;
    }

    public interface AnswerListDemander {
        AnswerListLoadListener getAnswerListLoadListener();
    }

    public interface AnswerListLoadListener {
        void onSuccess(ArrayList<AnswerTransfer> diff);

        void onEmpty(int onPage);

        void onFailure();
    }

    public int getCurrentPage() {
        return currentPage;
    }

}
