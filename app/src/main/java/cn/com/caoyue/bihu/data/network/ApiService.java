package cn.com.caoyue.bihu.data.network;

import cn.com.caoyue.bihu.data.transfer.InformationTransfer;
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

}
