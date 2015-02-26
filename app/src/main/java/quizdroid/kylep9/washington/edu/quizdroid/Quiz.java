package quizdroid.kylep9.washington.edu.quizdroid;

import java.io.Serializable;

/**
 * Created by kylepeterson on 2/16/15.
 */
public class Quiz implements Serializable {
    private String question;
    private String[] answers;
    private int correctIndex;

    public Quiz(String question, String[] answers, int correctIndex) {
        this.question = question;
        this.answers = answers;
        this.correctIndex = correctIndex;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getAnswers() {
        return answers;
    }

    public int getCorrectIndex() {
        return correctIndex;
    }
}
