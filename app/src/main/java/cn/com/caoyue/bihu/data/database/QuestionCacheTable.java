package cn.com.caoyue.bihu.data.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "QuestionCache")
public class QuestionCacheTable extends Model {

    @Column(name = "qid")
    public String id;
    @Column(name = "title")
    public String title;
    @Column(name = "content")
    public String content;
    @Column(name = "bestAnswerId")
    public String bestAnswerId;
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
    @Column(name = "authorFace")
    public String authorFace;

    public QuestionCacheTable() {
        super();
    }

    public QuestionCacheTable(String id, String title, String content, String bestAnswerId, String date, String recent, String answerCount, String authorId, String authorName, String authorFace) {
        super();
        this.id = id;
        this.title = title;
        this.content = content;
        this.bestAnswerId = bestAnswerId;
        this.date = date;
        this.recent = recent;
        this.answerCount = answerCount;
        this.authorId = authorId;
        this.authorName = authorName;
        this.authorFace = authorFace;
    }
}
