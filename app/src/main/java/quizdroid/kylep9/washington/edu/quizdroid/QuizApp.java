package quizdroid.kylep9.washington.edu.quizdroid;

import android.app.Application;
import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kylepeterson on 2/16/15.
 */
public class QuizApp extends Application {
    private static QuizApp instance;
    public SimpleTopicRepo topicRepo;

    @Override
    public void onCreate() {
        super.onCreate();
        // Create topic Repo

        SimpleTopicRepo repo = createTopicRepo();
        initInstance(repo);
        Log.i("application", "QuizApp loaded and run");
    }

    public  void initInstance(SimpleTopicRepo repo) {
        if(instance == null) {

            instance = new QuizApp(repo);
        }

    }

    public static QuizApp getInstance() {
        return instance;
    }

    public QuizApp() {}
    private QuizApp(SimpleTopicRepo repo) {
        topicRepo = repo;
        Log.i("application", "Repo built in singleton: " + repo);

    }

    public SimpleTopicRepo getTopicRepository() {
        return topicRepo;
    }

    public SimpleTopicRepo createTopicRepo() {
        /*Map<String, Topic> map = new HashMap<String, Topic>();
        //Math
        String[] a1 = {"1 Pi", "2 Pi", "2 Pie", "4 Pi"};
        Quiz q1 = new Quiz("How many pis are in a circle?", a1, 1);
        String[] a2 = {"1", "42", "4", "2"};
        Quiz q2 = new Quiz("2 + 2", a2, 2);
        Quiz[] qs = {q1, q2};
        List<Quiz>  questions = new ArrayList<Quiz>();
        for(int i = 0; i < qs.length; i++) {
            questions.add(qs[i]);
        }
        Topic mathTopic = new Topic("Math", "Math Problems",
                "Mathematics is the science that deals with the logic of shape, quantity and arrangement.", questions);
        map.put("Math", mathTopic);

        // Physics
        String[] sa1 = {"Wires and cables", "the shock", "Electrons and protons", "I do not know"};
        Quiz sq1 = new Quiz("What is electricity?", sa1, 2);
        String[] sa2 = {"gravity man", "42", "apple man", "yes"};
        Quiz sq2 = new Quiz("Who is newton", sa2, 0);
        Quiz[] sqs = {sq1, sq2};
        List<Quiz>  squestions = new ArrayList<Quiz>();
        for(int i = 0; i < sqs.length; i++) {
            squestions.add(sqs[i]);
        }
        Topic scienceTopic = new Topic("Physics", "Physics Problems",
                "Physics is the study of matter, energy, and the interaction between them", squestions);
        map.put("Physics", scienceTopic);

        // Marvel
        String[] ma1 = {"man with the webs", "wrestler hero", "sports guy", "gunslinger"};
        Quiz mq1 = new Quiz("Who is Spiderman?", ma1, 0);
        String[] ma2 = {"apple man", "Ltan See", "drawing man", "i can not say"};
        Quiz mq2 = new Quiz("Who is Stan Lee", ma2, 2);
        String[] ma3 = {"Your Tiny Brother", "man with no fear", "The man of iron", "Me"};
        Quiz mq3 = new Quiz("Who is Tony Stark?", ma3, 2);
        String[] ma4 = {"My Dad", "Your Dad", "Our Dad", "I dont know my dad"};
        Quiz mq4 = new Quiz("Who is Professor X?", ma4, 1);
        Quiz[] mqs = {mq1, mq2, mq3, mq4};
        List<Quiz>  mquestions = new ArrayList<Quiz>();
        for(int i = 0; i < mqs.length; i++) {
            mquestions.add(mqs[i]);
        }
        Topic marvelTopic = new Topic("Marvel Superheroes", "Marvel Problems",
                "Marvel is is an American publisher of comic books and related media.", mquestions);
        map.put("Marvel Superheroes", marvelTopic);

        // Create new repo containing all topics*/
        SimpleTopicRepo repo = readJSON();
        return repo;
    }

    public SimpleTopicRepo readJSON() {
        Map<String, Topic> jsonMap = new HashMap<String, Topic>();
        try {
            InputStream is = getAssets().open("quizdata.json");
            InputStreamReader isReader = new InputStreamReader(is);

            JsonReader reader = new JsonReader(isReader);
            List<Topic> topics = parseJSON(reader);
            for(Topic t: topics) {
                jsonMap.put(t.getTitle(), t);
            }
            is.close();
            isReader.close();
        } catch(IOException e) {
            Log.i("json", e.getMessage());
        }
        SimpleTopicRepo jsonRepo = new SimpleTopicRepo(jsonMap);
        return jsonRepo;
    }

    public List<Topic> parseJSON(JsonReader reader) throws IOException{
        List<Topic> topics = new ArrayList<Topic>();
        reader.beginArray();
        Log.i("json", "started reading topic array");
        while(reader.hasNext()) {
            Topic result = parseTopic(reader);
            Log.i("json", "resulting topic: " + result.getTitle());
            topics.add(result);
        }
        reader.endArray();
        Log.i("json", "finished reading topic array");
        return topics;
    }

    public Topic parseTopic(JsonReader reader) throws IOException {
        Topic currentTopic = new Topic();
        reader.beginObject();
        if (reader.nextName().equals("title")) {
            currentTopic.setTitle(reader.nextString());
            Log.i("json", "\tresulting title: " + currentTopic.getTitle());

        }
        if (reader.nextName().equals("desc")) {
            currentTopic.setLongDesc(reader.nextString());
            Log.i("json", "\tresulting desc: " + currentTopic.getLongDesc());

        }
        if (reader.nextName().equals("questions")) {
            List<Quiz> questions = new ArrayList<Quiz>();
            reader.beginArray();
            while (reader.hasNext()) {
                questions.add(parseQuestion(reader));
            }
            reader.endArray();
            currentTopic.setQuestions(questions);
        }
        reader.endObject();
        return currentTopic;
    }

    public Quiz parseQuestion(JsonReader reader) throws IOException {
        reader.beginObject();
        Quiz question = new Quiz();
        if(reader.nextName().equals("text")) {
            question.setQuestion(reader.nextString());
            Log.i("json", "\t\tresulting question: " + question.getQuestion());

        }
        if(reader.nextName().equals("answer")) {
            question.setCorrectIndex(Integer.parseInt(reader.nextString()));
            Log.i("json", "\t\tresulting answer index: " + question.getCorrectIndex());

        }
        if(reader.nextName().equals("answers")) {
            reader.beginArray();
            List<String> answers = new ArrayList<String>();
            while(reader.hasNext()) {
                answers.add(reader.nextString());
            }
            reader.endArray();
            String[] answersArr = answers.toArray(new String[answers.size()]);

            question.setAnswers(answersArr);
            Log.i("json", "\t\tresulting answers: " + question.getAnswers());

        }
        reader.endObject();
        return question;
    }

}
