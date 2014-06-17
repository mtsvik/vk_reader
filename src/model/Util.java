package model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;


/**
 * Author: Mikhail Tsvik (tsvik@me.com)
 * Date: 16.06.14
 */

public class Util {

    public static String findKey(String source, String patternBegin, String patternEnd) {
        int startkey = source.indexOf(patternBegin);
        if (startkey > -1) {
            int stopkey = source.indexOf(patternEnd, startkey + patternBegin.length());
            if (stopkey > -1) {
                return source.substring(startkey + patternBegin.length(), stopkey);
            }
        }
        return null;
    }

    public static String[] getUserById(int id) throws IOException, JSONException {
        Query query = new Query();
        query.sendQuery("users.get", new String[] {"user_ids=" + id, "fields=" + "online"});
        JSONObject JSONUser = new JSONObject(query.getResponseText());
        String firstName = JSONUser.getJSONArray("response").getJSONObject(0).getString("first_name");
        String lastname = JSONUser.getJSONArray("response").getJSONObject(0).getString("last_name");
        int onlineQuery = JSONUser.getJSONArray("response").getJSONObject(0).getInt("online");
        String online;
        if (onlineQuery > 0) {
            online = "online";
        } else online = "offline";
        String fullName = firstName + " " + lastname;
        return new String[] {fullName, online};
    }

    public static String[] getGruopById(int id) throws IOException, JSONException {
        Query query = new Query();
        query.sendQuery("groups.getById", new String[] {"group_id=" + id, "fields=members_count"});
        JSONObject JSONUser = new JSONObject(query.getResponseText());
        String name = JSONUser.getJSONArray("response").getJSONObject(0).getString("name");
        return new String[] {name};
    }

    public static String[] getPosterById(int id) throws IOException, JSONException {
        String poster;
        String online;
        if (id > 0) {
            poster = getUserById(id)[0];
            online = getUserById(id)[1];
            return new String[] {poster, online};
        } else {
            poster = getGruopById(Math.abs(id))[0];
            return new String[] {poster};
        }
    }

    public static String feedToString(ArrayList<Item> feed) throws IOException, JSONException {
        String output = "";
        int postCounter = 1;

        for (Item qwe : feed) {

            String headerOfPost = "==========================================  " + postCounter + "  ==========================================" + "\n";
            String footerOfPost = "=====================================" + "==" + "==================================================" + "\n\n\n\n";
            headerOfPost = "___________________________________________________________________________________________" + "\n" +
                    "-------------------------------------------------------------------------------------------" +
                    "" +
                    "" +
                    "" +
                    "" +
                    "" +
                    "" +
                    "" +
                    "" +
                    "" +
                    "" +
                    "" +
                    "" +
                    "" +
                    "" +
                    "" +
                    "" +
                    "" +
                    "" +
                    "" +
                    "\n";
            footerOfPost = "___________________________________________________________________________________________" + "\n" +
                    "-------------------------------------------------------------------------------------------" +
                    "\n\n\n";

            int[] posts_stats = new int[] {qwe.get_count_of_likes(), qwe.get_count_of_comments(), qwe.get_count_of_reposts()};
            String repost = qwe.get_post_type();
            int owner_id = Math.abs(qwe.get_owner_id());
            String post_type = qwe.get_type();
            String poster = "";
            String online = "";
            int id = qwe.get_owner_id();
            boolean user = false;
            if (id > 0) user = true;
            poster = getPosterById(id)[0];
            if (user) online = getPosterById(id)[1];
            String posted_time = relativeTime(qwe.get_date());
            String text = qwe.get_text();


            output += headerOfPost;
            postCounter++;
            output += "Poster: " + poster + "\n";
            if (user) output += "Status: " + online + "\n";
            if (user) output += "User id: " + owner_id + "\n";
            else output += "Group id: " + owner_id + "\n";

            if (post_type.equals("photo") || (post_type.equals("photo_tag"))) {
                output += "Post type: " + post_type + "\n";
            }
            if (repost.equals("copy") || repost == null) {
                output += "Repost: [" + getPosterById(qwe.get_copy_owner_id())[0] + "]\n";
            }
            output += "\n\n";

            try {
                String newStr = text.replaceAll("(.{90})", "$1|");
                String[] newStrings = newStr.split("\\|");
                for (int i = 0; i < newStrings.length; i++) {
                    output += newStrings[i].trim() + "\n";
                }
            } catch (NullPointerException e) {

            }


            output += "\n\n\n\n\n\n\n\n\n\n\n" + posted_time + "";
            if (post_type.equals("post")) {
                output += "                                    Comments " + posts_stats[1] + "    " +
                        "Reposts: " + posts_stats[2] + "    " +
                        "Likes: " + posts_stats[0];
            }
            output += "\n";

            output += footerOfPost;
        }

        return output;
    }

    public static void writeToFile(String toWrite, String path) throws IOException {
        File file = new File(path);
        FileOutputStream fileOutputStream = new FileOutputStream(file, false);
        FileChannel fileChannel = fileOutputStream.getChannel();
        ByteBuffer byteBuffer = null;
        byteBuffer = ByteBuffer.wrap(toWrite.getBytes(Charset.forName("UTF-8")));
        fileChannel.write(byteBuffer);
        fileChannel.close();
        fileOutputStream.close();
    }

    public static String unixTimestampToStringDate(long date) {
        String str = null;
        long d = date * 1000L;
        str = new java.text.SimpleDateFormat("dd/mm/yyyy").format(new java.util.Date(d));
        return str;
    }

    public static String relativeTime(long timeStamp) {
        java.util.Date time = new java.util.Date(timeStamp * 1000);
        long test = time.getTime();
        long milliseconds = System.currentTimeMillis();
        long diff = milliseconds - test;
        long diffSeconds = diff / 1000;
        long diffMinutes = diff / (60 * 1000);
        long diffHours = diff / (60 * 60 * 1000);
        long diffDays = diff / (24 * 60 * 60 * 1000);
        String relativeTime = null;
        if (diffDays > 1) {
            relativeTime = diffDays + " days ago";
        } else if (diffDays > 0) {
            relativeTime = diffDays + " days " + diffHours + " hours ago";
        } else if (diffHours > 1) {
            relativeTime = diffHours + " hours ago";
        } else if (diffMinutes > 0) {
            relativeTime = diffMinutes + " minutes ago";
        } else if (diffSeconds > 0) {
            relativeTime = diffSeconds + " seconds ago";
        }
        return relativeTime;
    }
}


