package sample;
import java.io.Serializable;
public class Question implements Serializable {
    String question;
    String answerHTML;
    int correctAnswers = 0;
    int wrongAnswers = 0;
    Question(String question, String answerHTML) {
        this.question = question;
        this.answerHTML = answerHTML;
    }
}
