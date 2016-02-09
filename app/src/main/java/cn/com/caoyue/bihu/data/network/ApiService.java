package cn.com.caoyue.bihu.data.network;

import cn.com.caoyue.bihu.data.transfer.AnswerListTransfer;
import cn.com.caoyue.bihu.data.transfer.InformationTransfer;
import cn.com.caoyue.bihu.data.transfer.QuestionListTransfer;
import cn.com.caoyue.bihu.data.transfer.UserTransfer;
import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {

    @FormUrlEncoded
    @POST("register.php")
    Call<InformationTransfer> register(@Field("apikey") String apikey, @Field("name") String name, @Field("password") String password);

    @FormUrlEncoded
    @POST("login.php")
    Call<UserTransfer> login(@Field("apikey") String apikey, @Field("name") String name, @Field("password") String password);

    @FormUrlEncoded
    @POST("loginWithOldToken.php")
    Call<UserTransfer> loginWithOldToken(@Field("apikey") String apikey, @Field("token") String token);

    @FormUrlEncoded
    @POST("getQuestionList.php")
    Call<QuestionListTransfer> getQuestionList(@Field("apikey") String apikey, @Field("page") String page);

    @FormUrlEncoded
    @POST("getAnswerList.php")
    Call<AnswerListTransfer> getAnswerList(@Field("apikey") String apikey, @Field("questionId") String questionId, @Field("page") String page, @Field("desc") String desc);

}
