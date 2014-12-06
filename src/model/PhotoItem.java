package model;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Author: Mikhail Tsvik (tsvik@me.com)
 * Date: 17.06.14
 */

public class PhotoItem implements Item {

    private String type;
    private int date;
    private int owner_id;
    private ArrayList<Photo> photos;

    public PhotoItem(String type, int date, int owner_id, ArrayList<Photo> photos) {
        this.type = type;
        this.date = date;
        this.owner_id = owner_id;
        this.photos = photos;
    }

    @Override
    public String get_type() {
        return type;
    }

    @Override
    public int get_owner_id() {
        return owner_id;
    }

    @Override
    public int get_date() {
        return date;
    }

    @Override
    public int get_item_id() {
        return 0;
    }

    @Override
    public String get_post_type() {
        return "";
    }

    @Override
    public int get_copy_owner_id() {
        return 0;
    }

    @Override
    public int get_copy_post_id() {
        return 0;
    }

    @Override
    public int get_copy_post_date() {
        return 0;
    }

    @Override
    public String get_text() {
        return null;
    }

    @Override
    public int get_count_of_comments() {
        return 0;
    }

    @Override
    public int get_count_of_likes() {
        return 0;
    }

    @Override
    public int get_count_of_reposts() {
        return 0;
    }

    @Override
    public String get_image_src() {
        return null;
    }

    @Override
    public int get_count_of_photos() {
        return photos.size();
    }

    @Override
    public Attachments get_attachments() {
        return null;
    }

    @Override
    public ArrayList<Photo> get_photos() {
        return photos;
    }





    @Override
    public String getPosterName() throws IOException, JSONException {
        return Util.getPosterById(owner_id)[0];
    }

    @Override
    public String getStatus() throws IOException, JSONException {
        String online = null;
        boolean user = false;
        if (owner_id > 0) user = true;
        if (user) online = Util.getPosterById(owner_id)[1];
        return online;
    }

    @Override
    public String getPostTime() {
        return Util.relativeTime(date);
    }
}
