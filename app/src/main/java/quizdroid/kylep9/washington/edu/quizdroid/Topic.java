package quizdroid.kylep9.washington.edu.quizdroid;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kylepeterson on 2/16/15.
 */
public class Topic implements Serializable{
    private String title;
    private String shortDesc;
    private String longDesc;
    private List<Quiz> questions;
    private int currentQuestion;

    public Topic(String title, String shortDesc, String longDesc, List<Quiz> questions) {
        this.title = title;
        this.shortDesc = shortDesc;
        this.longDesc = longDesc;
        this.questions = questions;
        currentQuestion = 0;
    }

    public String getTitle() {
        return title;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public String getLongDesc() {
        return longDesc;
    }

    public List<Quiz> getQuestions() {
        List<Quiz> result = new ArrayList<Quiz>();
        for (int i = 0; i < questions.size(); i++) {
            result.add(questions.get(i));
        }
        return result;
    }

    public Quiz getQuestion(int i) {
        return questions.get(i);
    }

    public int getCurrentQuestion() {
        return currentQuestion;
    }

    public void increaseIndex() {
        currentQuestion++;
    }

    public void resetIndex() {
        currentQuestion = 0;
    }

}
