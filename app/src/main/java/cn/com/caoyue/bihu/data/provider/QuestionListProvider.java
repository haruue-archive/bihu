package cn.com.caoyue.bihu.data.provider;

import com.jude.utils.JUtils;

import java.util.ArrayList;

import cn.com.caoyue.bihu.data.network.ApiKeys;
import cn.com.caoyue.bihu.data.network.RestApi;
import cn.com.caoyue.bihu.data.transfer.QuestionListTransfer;
import cn.com.caoyue.bihu.data.transfer.QuestionTransfer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionListProvider {

    private ArrayList<QuestionTransfer> array;
    private int currentPage;
    private QuestionListDemander demander;
    private static QuestionListProvider provider;

    public static QuestionListProvider getInstance(QuestionListDemander demander) {
        if (null == provider) {
            provider = new QuestionListProvider();
            provider.init();
        }
        provider.demander = demander;
        return provider;
    }

    public ArrayList<QuestionTransfer> getArray() {
        return array;
    }

    private void init() {
        array = new ArrayList<>(0);
        currentPage = 0;
        loadPage(currentPage);
    }

    public void loadPage(final int page) {
        JUtils.Log("inQuestionListProvider_current", currentPage + "");
        JUtils.Log("inQuestionListProvider_toLoad", page + "");
        Call<QuestionListTransfer> getQuestionListCall = RestApi.getHaruueKnowWebApiService().getQuestionList(ApiKeys.HARUUE_KNOW_WEB_APIKEY, page + "");
        getQuestionListCall.enqueue(new Callback<QuestionListTransfer>() {
            @Override
            public void onResponse(Response<QuestionListTransfer> response) {
                if (response.code() == 200) {
                    if (null == response.body().questions) {
                        demander.getQuestionListLoadListener().onEmpty(page);
                        return;
                    }
                    ArrayList<QuestionTransfer> diff = new ArrayList<>(0);
                    for (QuestionTransfer i : response.body().questions) {
                        if (!array.contains(i)) {
                            array.add(i);
                            diff.add(i);
                        }
                    }
                    currentPage = page;
                    demander.getQuestionListLoadListener().onSuccess(diff);
                } else {
                    demander.getQuestionListLoadListener().onFailure();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                demander.getQuestionListLoadListener().onFailure();
            }
        });
    }

    public void loadNextPage() {
        loadPage(currentPage + 1);
    }

    public void refresh() {
        currentPage = 0;
        array.clear();
        loadPage(currentPage);
    }

    public interface QuestionListDemander {
        QuestionListLoadListener getQuestionListLoadListener();
    }

    public interface QuestionListLoadListener {
        void onSuccess(ArrayList<QuestionTransfer> diff);

        void onEmpty(int onPage);

        void onFailure();
    }

    public int getCurrentPage() {
        return currentPage;
    }
}
