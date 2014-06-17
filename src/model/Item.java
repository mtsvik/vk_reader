package model;

import java.util.ArrayList;

/**
 * Author: Mikhail Tsvik (tsvik@me.com)
 * Date: 17.06.14
 */

public interface Item {
    String get_type();
    int get_owner_id();
    int get_date();
    int get_item_id();
    String get_post_type();
    int get_copy_owner_id();
    int get_copy_post_id();
    int get_copy_post_date();
    String get_text();
    int get_count_of_comments();
    int get_count_of_likes();
    int get_count_of_reposts();
    String get_image_src();
    int get_count_of_photos();
    Attachments get_attachments();
    ArrayList<Photo> get_photos();

}
