package cn.com.caoyue.bihu.data.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "QuestionCache")
public class QuestionCacheTable extends Model {

    @Column(name = "id")
    public String id;
    @Column(name = "title")
    public String title;
    @Column(name = "content")
    public String content;
    @Column(name = "bestAnswerId")
    public Object bestAnswerId;
    @Column(name = "date")
    public String date;
    @Column(name = "recent")
    public String recent;
    @Column(name = "answerCount")
    public String answerCount;
    @Column(name = "authorId")
    public String authorId;
    @Column(name = "authorName")
    public String authorName;
    @Column(name = "authorName")
    public Object authorFace;

}
