package sample;

import java.util.Comparator;

public class Cmprt implements Comparator<Question> {
    public int compare(Question q1, Question q2) {
        int ans = q1.correctAnswers - q2.correctAnswers - q1.wrongAnswers * 2 + q2.wrongAnswers * 2;
        return Integer.compare(ans, 0);
    }

}
