package cn.com.caoyue.bihu.data.transfer;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class QuestionTransfer {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("content")
    @Expose
    public String content;
    @SerializedName("bestAnswerId")
    @Expose
    public Object bestAnswerId;
    @SerializedName("date")
    @Expose
    public String date;
    @SerializedName("recent")
    @Expose
    public String recent;
    @SerializedName("answerCount")
    @Expose
    public String answerCount;
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
