package model;


import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.URISyntaxException;
import java.net.URLEncoder;

/**
 * Author: Mikhail Tsvik (tsvik@me.com)
 * Date: 14.06.14
 */

public class Authorization {

    private String client_id = "4413336";
    private String scope = "wall,friends,photos,notifications,notify";
    private String redirect_uri = "https://oauth.vk.com/blank.html";
    private String display = "page";
    private String response_type = "token";
    private String login;
    private String password;
    private static String access_token;
    private int uid;
    private boolean isLoggedIn = false;
    private Query query = new Query();

    public void doLogin(String login, String password) throws IOException, URISyntaxException {
        CookieHandler.setDefault(new CookieManager());
        this.login = login;
        this.password = password;

        String URL = "http://oauth.vk.com/authorize?" + "client_id=" + client_id + "&scope=" + scope + "&redirect_uri=" +
                redirect_uri + "&display=" + display + "&response_type=" + response_type;
        query.sendQuery(URL);
        System.out.println("First query: \n    Query: " + URL + "\n    Redirect to: " + query.getRedirectTo());

        String ip_h = Util.findKey(query.getResponseText(), "name=\"ip_h\" value=\"", "\"");
        String to = Util.findKey(query.getResponseText(), "name=\"to\" value=\"", "\"");
        System.out.println("        Get ip_h and to:" + "\n" +
                "        ip_h: " + ip_h + "\n" +
                "        to: " + to + "\n");

        /*
         * Fill the login form and post it. The response will redirect to:
		 * 1. ACCESS_TOKEN obtaining;
		 * 2. to the permissions granting, if user runs the app for the first time, or the permissions have changed;
		 * 3. to the login form if either login or password are incorrect.
	    */
        String loginURL = "https://login.vk.com/?act=login&soft=1&utf8=1" + "&q=1" + "&ip_h=" + ip_h + "&_origin=http://oauth.vk.com" +
                "&to=" + to + "&email=" + login + "&pass=" + URLEncoder.encode(password, "UTF-8");
        query.sendQuery(loginURL);
        System.out.println("Second query (authorization): \n    Query: " + loginURL +
                "\n    Redirect to: " + query.getRedirectTo() + "\n");
        URL = query.getRedirectTo();
        query.sendQuery(URL);
        System.out.println("Third query (going authorization redirect): \n    Query: " + URL +
                "\n    Redirect to: " + query.getRedirectTo() + "\n");

        /*
		 * 1. If it is redirect (redirect != null) we got redirect to ACCESS_TOKEN obtaining.
		 * 2. If not, it is POST form for permissions granting or the login form.
		 */
        if (query.getRedirectTo() == null) {
            URL = Util.findKey(query.getResponseText(), " action=\"", "\"");
            if (URL.startsWith("https://login.vk.com/?act=login&soft=1")) {
                throw new IllegalStateException("Wrong email/password");
            }
        } else {
            URL = query.getRedirectTo();
        }

        query.sendQuery(URL);

        access_token = query.getRedirectTo().split("#")[1]
                .split("&")[0]
                .split("=")[1];
        uid = Integer.parseInt(query.getRedirectTo().split("#")[1]
                .split("&")[2]
                .split("=")[1]);
        isLoggedIn = true;
        System.out.println("Authorization done!\n    Token: " + access_token + "\n    User id: " + uid + "\n");
    }

    public void doLogout() {
        isLoggedIn = false;
    }

    public String getAccessToken() {
        return access_token;
    }
}
