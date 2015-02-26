package quizdroid.kylep9.washington.edu.quizdroid;

/**
 * Created by kylepeterson on 2/16/15.
 */
public interface TopicRepository {

    public Topic getTopic(String name);

    public void addTopic(String name, Topic t);

    public void deleteTopic(String topic);

    public void updateTopic(String name, Topic topic);

}
