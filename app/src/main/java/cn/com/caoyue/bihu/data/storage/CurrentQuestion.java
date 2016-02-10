package cn.com.caoyue.bihu.data.storage;

import cn.com.caoyue.bihu.data.transfer.QuestionTransfer;

public class CurrentQuestion {

    public String id;
    public String title;
    public String content;
    public String date;
    public String answerCount;
    public String authorId;
    public String authorName;
    public String authorFace;
    public int position;
    private boolean isStoraged = false;
    public static CurrentQuestion currentQuestion;

    public static CurrentQuestion getInstance() {
        if (null == currentQuestion) {
            currentQuestion = new CurrentQuestion();
        }
        return currentQuestion;
    }

    public void storage(QuestionTransfer transfer, int position) {
        this.id = transfer.id;
        this.title = transfer.title;
        this.content = transfer.content;
        this.date = transfer.date;
        this.answerCount = transfer.answerCount;
        this.authorId = transfer.authorId;
        this.authorName = transfer.authorName;
        this.authorFace = transfer.authorFace;
        this.position = position;
        this.isStoraged = true;
    }

    public static void clean() {
        currentQuestion = null;
    }

    public static boolean isStoraged() {
        if (null == currentQuestion) {
            return false;
        } else {
            return currentQuestion.isStoraged;
        }
    }
}
