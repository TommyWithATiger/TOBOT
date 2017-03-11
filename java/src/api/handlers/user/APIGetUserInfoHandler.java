package api.handlers.user;

import static api.helpers.RequestMethodHelper.checkRequestMethod;
import static api.helpers.isLoggedInHelper.getUserPost;

import data.user.User;
import org.apache.http.HttpRequest;

public class APIGetUserInfoHandler {

  /**
   * An API handler for a user to get it's user information. Requires the user to be logged in.
   *
   * @param httpRequest the request to handle
   * @return A JSON object with the following variables:
   *        username (String): the username
   *        token (String): the user token
   *        userType (String): The user type
   *        email (String): the user email
   */
  public static String getUserInfo(HttpRequest httpRequest) {
    checkRequestMethod("POST", httpRequest);
    User user = getUserPost(httpRequest, ", cannot get user info");
    return user.createAbout().toString();
  }

}
