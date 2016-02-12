package cn.com.caoyue.bihu.data.transfer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class UploadInformationTransfer {

    @SerializedName("server")
    @Expose
    public String server;
    @SerializedName("file")
    @Expose
    public String file;
    @SerializedName("url")
    @Expose
    public String url;

}
