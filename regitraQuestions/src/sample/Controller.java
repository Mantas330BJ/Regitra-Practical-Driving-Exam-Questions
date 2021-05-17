package sample;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.web.WebView;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;


public class Controller {
    public Label questionLabel;
    public WebView screen;
    public Button answerButton;
    public Button correctAnswerButton;
    public Button newQuestionButton;
    public Button wrongAnswerButton;
    PriorityQueue<Question> questions = new PriorityQueue<>(new Cmprt());
    Queue<Question> next = new LinkedList<>();
    Question q = null;
    int questionDelay = 2;

    public void initialize() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("C:\\regitraQuestions\\src\\sample\\questions.ser"));
        ArrayList<Question> qs = (ArrayList<Question>) in.readObject();
        in.close();
        questions.addAll(qs);

        for (int i = 0; i < questionDelay; ++i)
            next.add(null);
        nextQuestion();
        questionLabel.setText(q.question);
        questionLabel.setAlignment(Pos.CENTER);
    }

    public void showAnswer() {
        screen.getEngine().loadContent(q.answerHTML, "text/html");
    }

    public void correctAnswer() {
        ++q.correctAnswers;
    }

    public void wrongAnswer() {
        ++q.wrongAnswers;
    }

    public void nextQuestion() {
        if (next.peek() != null) {
            questions.add(next.peek());
        }
        next.poll();
        screen.getEngine().loadContent("");
        q = questions.poll();
        questionLabel.setText(q.question);
        next.add(q);
    }
}
