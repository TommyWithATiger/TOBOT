package api;

import api.exceptions.APIHandlerNotFoundException;
import java.util.HashMap;
import java.util.function.Function;
import org.apache.http.HttpRequest;

public class APIDelegator {

  private static HashMap<String, Function<HttpRequest, String>> handlerRegistry = new HashMap<>();

  public static String delegate(HttpRequest APIRequest) throws APIHandlerNotFoundException{
    String uri = APIRequest.getRequestLine().getUri().substring(4);
    for (String uriRegex : handlerRegistry.keySet()) {
      if (uri.matches(uriRegex)) {
        return handlerRegistry.get(uriRegex).apply(APIRequest);
      }
    }

    throw new APIHandlerNotFoundException("Could not find a handler that matches the API call");
  }

  private static boolean register(String regex, Function<HttpRequest, String> APIHandler){
    if (handlerRegistry.containsKey(regex)) return false;
    handlerRegistry.put(regex, APIHandler);
    return true;
  }

  public static void populateRegistry(){

  }

}
