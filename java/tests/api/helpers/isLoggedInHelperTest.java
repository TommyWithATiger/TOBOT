package api.helpers;

import static api.helpers.isLoggedInHelper.getUserFromRequest;
import static junit.framework.TestCase.assertEquals;

import api.exceptions.APIRequestForbiddenException;
import base.BaseTest;
import data.user.User;
import org.apache.http.HttpRequest;
import org.apache.http.message.BasicHttpRequest;
import org.junit.Test;

public class isLoggedInHelperTest extends BaseTest {

  @Test
  public void testValidLogin() {
    User user = new User("user", "valid@email.com", "qwerty");
    user.create();

    user.createSessionToken();
    user.update();

    HttpRequest httpRequest = new BasicHttpRequest("GET", "URL");
    httpRequest.addHeader("X-Username", user.getUsername());
    httpRequest.addHeader("Authorization", "Bearer " + user.getSessionToken());

    assertEquals(user, getUserFromRequest(httpRequest, ""));
  }

  @Test(expected = APIRequestForbiddenException.class)
  public void testMissingHeaders() {
    getUserFromRequest(new BasicHttpRequest("GET", "URL"), "");
    //assertFalse(isLoggedIn(new BasicHttpRequest("GET", "URL")));
  }

  @Test(expected = APIRequestForbiddenException.class)
  public void testMissingUsernameHeader() {
    User user = new User("user", "valid@email.com", "qwerty");
    user.create();

    user.createSessionToken();
    user.update();

    HttpRequest httpRequest = new BasicHttpRequest("GET", "URL");
    httpRequest.addHeader("Authorization", "Bearer " + user.getSessionToken());

    getUserFromRequest(httpRequest, "");
  }

  @Test(expected = APIRequestForbiddenException.class)
  public void testMissingAuthorizationHeader() {
    User user = new User("user", "valid@email.com", "qwerty");
    user.create();

    user.createSessionToken();
    user.update();

    HttpRequest httpRequest = new BasicHttpRequest("GET", "URL");
    httpRequest.addHeader("X-Username", user.getUsername());

    getUserFromRequest(httpRequest, "");
  }

  @Test(expected = APIRequestForbiddenException.class)
  public void testInvalidLogin() {
    HttpRequest httpRequest = new BasicHttpRequest("GET", "URL");
    httpRequest.addHeader("X-Username", "admin");
    httpRequest.addHeader("Authorization", "Bearer: adshjasdkhk");

    getUserFromRequest(httpRequest, "");
  }

}
