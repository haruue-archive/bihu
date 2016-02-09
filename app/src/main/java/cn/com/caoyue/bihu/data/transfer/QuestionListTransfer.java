package cn.com.caoyue.bihu.data.transfer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class QuestionListTransfer {

    @SerializedName("totalCount")
    @Expose
    public Long totalCount;
    @SerializedName("totalPage")
    @Expose
    public Long totalPage;
    @SerializedName("questions")
    @Expose
    public List<QuestionTransfer> questions = new ArrayList<QuestionTransfer>();
    @SerializedName("curPage")
    @Expose
    public String curPage;

}
