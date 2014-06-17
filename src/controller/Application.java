package controller;

import model.Feed;
import model.Item;
import model.Util;
import org.json.JSONException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * Author: Mikhail Tsvik (tsvik@me.com)
 * Date: 14.06.14
 */

public class Application {

    public static void main(String[] args) throws IOException, URISyntaxException, JSONException {
        // MainFrame mainFrame = new MainFrame();
//        Authorization auth = new Authorization();
//        auth.doLogin("tsvik@me.com", "shapdasha123");
        String access_token = "942fa4261f3017367ac9a76c322ae0a71c67eae1242c44eab52a3c63b1baada8d5f713c240329424cbde0";

        Feed feed = new Feed();
        ArrayList<Item> posts = feed.getFeed(access_token, "post,photo,photo_tag", 30);
        String feedOutput = Util.feedToString(posts);
        Util.writeToFile(feedOutput, "out.txt");
    }

}
