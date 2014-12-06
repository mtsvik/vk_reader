package model;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Author: Mikhail Tsvik (tsvik@me.com)
 * Date: 15.06.14
 */

public class PostItem implements Item {

    private String type;
    private int source_id;
    private int date;
    private int post_id;
    private String post_type;
    private int copy_owner_id;
    private int copy_post_id;
    private int copy_post_date;
    private String text;
    private int count_of_comments;
    private int count_of_likes;
    private int count_of_reposts;
    private Attachments attachments;

    public PostItem(String type, int source_id, int date, int post_id, String post_type, int copy_owner_id, int copy_post_date, int copy_post_id, String text, int count_of_comments, int count_of_likes, int count_of_reposts, Attachments attachments) {
        this.type = type;
        this.source_id = source_id;
        this.date = date;
        this.post_id = post_id;
        this.post_type = post_type;
        this.copy_owner_id = copy_owner_id;
        this.copy_post_date = copy_post_date;
        this.copy_post_id = copy_post_id;
        this.text = text;
        this.count_of_comments = count_of_comments;
        this.count_of_likes = count_of_likes;
        this.count_of_reposts = count_of_reposts;
        this.attachments = attachments;
    }

    @Override
    public String get_type() {
        return type;
    }

    @Override
    public int get_owner_id() {
        return source_id;
    }

    @Override
    public int get_date() {
        return date;
    }

    @Override
    public int get_item_id() {
        return post_id;
    }

    @Override
    public String get_post_type() {
        return post_type;
    }

    @Override
    public int get_copy_owner_id() {
        return copy_owner_id;
    }

    @Override
    public int get_copy_post_id() {
        return copy_post_id;
    }

    @Override
    public int get_copy_post_date() {
        return copy_post_date;
    }

    @Override
    public String get_text() {
        return text;
    }

    @Override
    public int get_count_of_comments() {
        return count_of_comments;
    }

    @Override
    public int get_count_of_likes() {
        return count_of_likes;
    }

    @Override
    public int get_count_of_reposts() {
        return count_of_reposts;
    }

    @Override
    public String get_image_src() {
        return null;
    }

    @Override
    public int get_count_of_photos() {
        if (attachments.getPhotos().size() == 0) return 0;
        return attachments.getPhotos().size();
    }

    @Override
    public Attachments get_attachments() {
        return attachments;
    }

    @Override
    public ArrayList<Photo> get_photos() {
       if (attachments.getPhotos()==null) return null;
        return attachments.getPhotos();
    }







    @Override
    public String getPosterName() throws IOException, JSONException {
        return Util.getPosterById(source_id)[0];
    }

    @Override
    public String getStatus() throws IOException, JSONException {
        String online = null;
        boolean user = false;
        if (source_id > 0) user = true;
        if (user) online = Util.getPosterById(source_id)[1];
        return online;
    }

    @Override
    public String getPostTime() {
        return Util.relativeTime(date);
    }


}
