package cn.com.caoyue.bihu.data.network;

import java.util.Map;

import cn.com.caoyue.bihu.data.transfer.AnswerListTransfer;
import cn.com.caoyue.bihu.data.transfer.InformationTransfer;
import cn.com.caoyue.bihu.data.transfer.QuestionListTransfer;
import cn.com.caoyue.bihu.data.transfer.UploadInformationTransfer;
import cn.com.caoyue.bihu.data.transfer.UserTransfer;
import okhttp3.RequestBody;
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

    @Multipart
    @POST("uploadImage.php")
    Call<UploadInformationTransfer> uploadImage(@PartMap Map<String, RequestBody> params);

    @FormUrlEncoded
    @POST("modifyFace.php")
    Call<InformationTransfer> modifyFace(@Field("apikey") String apikey, @Field("token") String token, @Field("face") String face);

    @FormUrlEncoded
    @POST("question.php")
    Call<InformationTransfer> question(@Field("apikey") String apikey, @Field("token") String token, @Field("title") String title, @Field("content") String content);

    @FormUrlEncoded
    @POST("answer.php")
    Call<InformationTransfer> answer(@Field("apikey") String apikey, @Field("token") String token, @Field("questionId") String questionId, @Field("content") String content);

}
