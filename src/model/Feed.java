package model;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Author: Mikhail Tsvik (tsvik@me.com)
 * Date: 17.06.14
 */

public class Feed {

    private ArrayList<Item> feed = new ArrayList<Item>();
    private Parser parser = new Parser();

    public ArrayList<Item> getFeed(String access_token, String filters, int countOfPosts) throws IOException, JSONException {
        Item item;
        JSONArray JSONFeed = parser.getJSONFeed(access_token, filters, countOfPosts);
        for (int i = 0; i < JSONFeed.length(); i++) {
            item = parser.JSONITtemtoItem(JSONFeed.getJSONObject(i));
            feed.add(item);
        }
        return feed;
    }

    public Item[] getItemsArray() {
        Item[] items = new Item[feed.size()];

        for (int i = 0; i < feed.size(); i++) {
            items[i] = feed.get(i);
        }
        return items;
    }


}
