package cn.com.caoyue.bihu.data.transfer;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class AnswerListTransfer {

    @SerializedName("totalCount")
    @Expose
    public String totalCount;
    @SerializedName("totalPage")
    @Expose
    public Long totalPage;
    @SerializedName("answers")
    @Expose
    public List<AnswerTransfer> answers = new ArrayList<AnswerTransfer>();
    @SerializedName("curPage")
    @Expose
    public String curPage;

}
