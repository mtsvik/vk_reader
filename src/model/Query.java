package model;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

/**
 * Author: Mikhail Tsvik (tsvik@me.com)
 * Date: 16.06.14
 */

public class Query {
    private String responseText;
    private String redirectTo;

    public void sendQuery(String query) throws IOException {
        URL url = new URL(query);
        URLConnection con = url.openConnection();
        InputStream in = con.getInputStream();
        Scanner s = new java.util.Scanner(in).useDelimiter("\\A");
        responseText =  s.hasNext() ? s.next() : "";
        redirectTo = con.getHeaderField("Location");
    }

    public void sendQuery(String method, String[] params) throws IOException {
        String URL = "https://api.vk.com/method/" + method + "?";
        StringBuilder sb = new StringBuilder();
        sb.append(URL);
        for (int i = 0; i < params.length; i++) {
            sb.append("&").append(params[i]);
        }
        sendQuery(sb.toString());
    }

    public void sendQuery(String method, String access_token, String[] params) throws IOException {
        String URL = "https://api.vk.com/method/" + method + "?";
        StringBuilder sb = new StringBuilder();
        sb.append(URL);
        for (int i = 0; i < params.length; i++) {
            sb.append("&").append(params[i]);
        }
        sb.append("&access_token=").append(access_token);
        sendQuery(sb.toString());
    }

    public String getResponseText() {
        return responseText;
    }

    public String getRedirectTo() {
        return redirectTo;
    }
}
