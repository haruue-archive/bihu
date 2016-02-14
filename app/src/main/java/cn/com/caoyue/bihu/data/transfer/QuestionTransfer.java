package cn.com.caoyue.bihu.data.transfer;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import cn.com.caoyue.util.time.Time;

@Generated("org.jsonschema2pojo")
public class QuestionTransfer implements Comparable<QuestionTransfer>, Serializable {

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
    public String bestAnswerId;
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
    public String authorFace;

    @Override
    public int compareTo(QuestionTransfer another) {
        long timeStampThis = new Time(this.date, "y-M-d H:m:s").getTimeStamp();
        long timeStampAnother = new Time(another.date, "y-M-d H:m:s").getTimeStamp();
        if (timeStampThis < timeStampAnother) return -1;
        else if (timeStampThis > timeStampAnother) return 1;
        else return 0;  // timeSpampThis == timeStampAnother
    }

    @Override
    public boolean equals(Object o) {
        return this.id == ((QuestionTransfer) o).id;
    }
}
