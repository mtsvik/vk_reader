package controller;

import model.Authorization;
import model.Feed;
import model.Item;
import model.Util;
import org.json.JSONException;
import view.MainFrame;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * Author: Mikhail Tsvik (tsvik@me.com)
 * Date: 14.06.14
 */

public class Application {

    public static void main(String[] args) throws IOException, URISyntaxException, JSONException {
        Authorization auth = new Authorization();
        auth.doLogin("login", "password");
        String access_token = "token_here";

        Feed feed = new Feed();
        ArrayList<Item> posts = feed.getFeed(access_token, "post,photo,photo_tag", 10);
        String feedOutput = Util.feedToString(posts);
        Util.writeToFile(feedOutput, "out.txt");
//        System.out.println(feedOutput);


        MainFrame mainFrame = new MainFrame(feed.getItemsArray());


    }

}
