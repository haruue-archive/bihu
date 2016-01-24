package cn.com.caoyue.bihu.data.transfer;

import android.support.annotation.Nullable;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class UserTransfer {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("face")
    @Expose
    @Nullable
    public String face;
    @SerializedName("password")
    @Expose
    public String password;
    @SerializedName("token")
    @Expose
    public String token;

}
