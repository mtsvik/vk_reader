package model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Author: Mikhail Tsvik (tsvik@me.com)
 * Date: 17.06.14
 */

public class Parser {

    /**
     * @param access_token - access token
     * @param filters      - type of posts
     * @param countOfPosts - count of posts
     *
     * @return JSON array of posts
     */
    public JSONArray getJSONFeed(String access_token, String filters, int countOfPosts) throws IOException, JSONException {
        Query query = new Query();
        query.sendQuery("newsfeed.get", access_token, new String[] {"filters=" + filters, "count=" + countOfPosts});
        JSONArray data = new JSONObject(query.getResponseText()).getJSONObject("response").getJSONArray("items");
        return data;
    }

    /**
     * @param JSONItem - JSONObject of item (post or photo)
     *
     * @return VK item (post item or photo item)
     */
    public Item JSONITtemtoItem(JSONObject JSONItem) throws JSONException {
        int source_id = JSONItem.getInt("source_id");
        int date = JSONItem.getInt("date");
        String type = JSONItem.getString("type");
        ArrayList<Photo> photos = new ArrayList<Photo>();

        if (type.equals("post")) {
            int copy_owner_id = 0;
            int copy_post_date = 0;
            int copy_post_id = 0;
            int post_id = JSONItem.getInt("post_id");
            String post_type = JSONItem.getString("post_type");
            if (post_type.equals("copy")) {
                copy_owner_id = JSONItem.getInt("copy_owner_id");
                copy_post_date = JSONItem.getInt("copy_post_date");
                copy_post_id = JSONItem.getInt("copy_post_id");
            }
            String text = JSONItem.getString("text");
            int count_of_comments = JSONItem.getJSONObject("comments").getInt("count");
            int count_of_likes = JSONItem.getJSONObject("likes").getInt("count");
            int count_of_reposts = JSONItem.getJSONObject("reposts").getInt("count");

            JSONArray JSONAttachments;
            try {
                JSONAttachments = JSONItem.getJSONArray("attachments");
            } catch (JSONException e) {
                JSONAttachments = null;
            }
            Attachments attachments = JSONAttachmentsToAttachments(JSONAttachments);

            return new PostItem(type, source_id, date, post_id, post_type, copy_owner_id, copy_post_date, copy_post_id, text, count_of_comments, count_of_likes, count_of_reposts, attachments);
        }

        if (type.equals("photo") || type.equals("photo_tag")) {
            JSONArray array = JSONItem.getJSONArray(type + "s");
            Photo photo;
            int count_of_photos = array.getInt(0);
            if (count_of_photos > 5) count_of_photos = 5;
            for (int k = 1; k < count_of_photos; k++) {
                photo = JSONPhotoToPhoto(array.getJSONObject(k));
                photos.add(photo);
            }
            return new PhotoItem(type, date, source_id, photos);
        }
        return null;
    }

    /**
     * @param JSONAttachments - JSONObject of attachments
     *
     * @return attachment object for VK
     */
    public Attachments JSONAttachmentsToAttachments(JSONArray JSONAttachments) throws JSONException {
        if (JSONAttachments == null) return null;
        int length = JSONAttachments.length();
        if (length > 3) length = 3;

        ArrayList<Attachments.Audio> audios = new ArrayList<Attachments.Audio>();
        ArrayList<Attachments.Video> videos = new ArrayList<Attachments.Video>();
        ArrayList<Photo> photos = new ArrayList<Photo>();
        Attachments.Link link = null;

        for (int i = 0; i < length; i++) {
            if (JSONAttachments.getJSONObject(i).getString("type").equals("link")) {
                link = JSONLinkToLink(JSONAttachments.getJSONObject(i).getJSONObject("link"));
            }
            if (JSONAttachments.getJSONObject(i).getString("type").equals("photo")) {
                photos.add(JSONPhotoToPhoto(JSONAttachments.getJSONObject(i).getJSONObject("photo")));
            }
            if (JSONAttachments.getJSONObject(i).getString("type").equals("video")) {
                videos.add(JSONVideoToVideo(JSONAttachments.getJSONObject(i).getJSONObject("video")));
            }
            if (JSONAttachments.getJSONObject(i).getString("type").equals("audio")) {
                audios.add(JSONAudioToAudio(JSONAttachments.getJSONObject(i).getJSONObject("audio")));
            }
        }
        return new Attachments(videos, audios, photos, link);
    }

    /**
     * @param JSONPhoto - JSONObject of one photo
     *
     * @return one VK photo object
     */
    public Photo JSONPhotoToPhoto(JSONObject JSONPhoto) throws JSONException {
        String text = JSONPhoto.getString("text");
        int owner_id = JSONPhoto.getInt("owner_id");
        int photo_id = JSONPhoto.getInt("pid");
        String image_src = JSONPhoto.getString("src_big");
        return new Photo(photo_id, owner_id, image_src, text);
    }

    /**
     * @param JSONLink - JSONObject of one link
     *
     * @return one VK link object
     */
    public Attachments.Link JSONLinkToLink(JSONObject JSONLink) throws JSONException {
        String title = JSONLink.getString("title");
        String URL = JSONLink.getString("url");
        String description = JSONLink.getString("description");
        return new Attachments.Link(title, URL, description);
    }

    /**
     * @param JSONVideo - JSONObject of one video
     *
     * @return one VK video object
     */
    public Attachments.Video JSONVideoToVideo(JSONObject JSONVideo) throws JSONException {
        String title = JSONVideo.getString("title");
        int duration = JSONVideo.getInt("duration");
        int vid = JSONVideo.getInt("vid");
        String image = JSONVideo.getString("image");
        return new Attachments.Video(vid, title, duration, image);
    }

    /**
     * @param JSONAudio - JSONObject of one audio
     *
     * @return one VK audio object
     */
    public Attachments.Audio JSONAudioToAudio(JSONObject JSONAudio) throws JSONException {
        int id = JSONAudio.getInt("aid");
        int duration = JSONAudio.getInt("duration");
        String artist = JSONAudio.getString("artist");
        String title = JSONAudio.getString("title");
        String URL = JSONAudio.getString("url");
        return new Attachments.Audio(artist, title, URL, duration, id);
    }
}
