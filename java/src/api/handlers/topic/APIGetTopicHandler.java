package api.handlers.topic;

import static api.helpers.RequestMethodHelper.checkRequestMethod;
import static api.helpers.UrlArgumentHelper.getArgumentsInURL;
import static api.helpers.UrlArgumentHelper.requireURIFields;

import api.exceptions.APIBadRequestException;
import data.dao.TopicDAO;
import data.Topic;
import java.util.HashMap;
import java.util.List;
import org.apache.http.HttpRequest;
import org.json.JSONArray;
import org.json.JSONObject;

public class APIGetTopicHandler {

  /**
   * An API handler for getting information about a topic given its id. Require the following data:
   *        id (int): the topic id
   *
   * @param httpRequest The request to handle
   * @return A JSON object on the form of the createAboutTopic method.
   */
  public static String getTopicByID(HttpRequest httpRequest){
    checkRequestMethod("GET", httpRequest);

    HashMap<String, String> uriArguments = getArgumentsInURL(httpRequest);

    if (!uriArguments.containsKey("id")) {
      throw new APIBadRequestException("id must be given");
    }

    int topicID;

    // Subject id must be integer
    try {
      topicID = Integer.parseInt(uriArguments.get("id"));
    } catch (NumberFormatException nfe) {
      throw new APIBadRequestException("id must be integer");
    }

    Topic topic = TopicDAO.getInstance().findById(topicID);

    if (topic == null){
      throw new APIBadRequestException("No topic with the given id");
    }

    return topic.createAbout().toString();
  }

  /**
   * An API handler for getting topics by title. Require the following data:
   *        title (String): the topic title
   *
   * @param httpRequest The request to handle
   * @return A JSON object with the variable topics which is an array of JSON objects on the form of
   * the createAboutTopic method for each of the topics.
   */
  public static String getTopicsByTitle(HttpRequest httpRequest){
    checkRequestMethod("GET", httpRequest);

    HashMap<String, String> uriArguments = getArgumentsInURL(httpRequest);

    requireURIFields(uriArguments, "title");

    String title = uriArguments.get("title");

    List<Topic> topics = TopicDAO.getInstance().findTopicsByTitle(title);

    JSONObject response = new JSONObject();
    JSONArray topicArray = new JSONArray();
    topics.forEach(topic -> topicArray.put(topic.createAbout()));
    response.put("topics", topicArray);
    return response.toString();
  }

  /**
   * An API handler for finding all topics
   *
   * @param httpRequest The request to handle
   * @return A JSON object with the variable topics which is an array of JSON objects on the form of
   * the createAboutTopic method for each of the topics.
   */
  public static String getAllTopics(HttpRequest httpRequest){
    checkRequestMethod("GET", httpRequest);

    List<Topic> topics = TopicDAO.getInstance().findAll();

    JSONObject response = new JSONObject();
    JSONArray topicArray = new JSONArray();
    topics.forEach(topic -> topicArray.put(topic.createAbout()));
    response.put("topics", topicArray);
    return response.toString();
  }

}
