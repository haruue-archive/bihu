package cn.com.caoyue.bihu.data.transfer;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class AnswerTransfer {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("content")
    @Expose
    public String content;
    @SerializedName("date")
    @Expose
    public String date;
    @SerializedName("authorId")
    @Expose
    public String authorId;
    @SerializedName("authorName")
    @Expose
    public String authorName;
    @SerializedName("authorFace")
    @Expose
    public Object authorFace;

}
