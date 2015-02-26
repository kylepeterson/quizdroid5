package quizdroid.kylep9.washington.edu.quizdroid;

import java.util.Map;
import java.util.Set;

/**
 * Created by kylepeterson on 2/16/15.
 */
public class SimpleTopicRepo implements TopicRepository {
    private Map<String, Topic> topics;

    public SimpleTopicRepo(Map<String, Topic> topics) {
        this.topics = topics;
    }

    public Topic getTopic(String name) {
        return topics.get(name);
    }

    public void addTopic(String name, Topic topic) {
        topics.put(name, topic);
    }

    public void deleteTopic(String name) {
        topics.remove(name);
    }

    public void updateTopic(String name, Topic topic) {
        addTopic(name, topic);
    }

    public Set<String> getTopicNames() {
        return topics.keySet();
    }

}
