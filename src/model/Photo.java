package model;

/**
 * Author: Mikhail Tsvik (tsvik@me.com)
 * Date: 17.06.14
 */

public class Photo {

    private int id;
    private int owner_id;
    private String src;
    private String text;

    public Photo(int id, int owner_id, String src, String text) {
        this.id = id;
        this.owner_id = owner_id;
        this.src = src;
        this.text = text;
    }

    public int get_id() {
        return id;
    }

    public int get_owner_id() {
        return owner_id;
    }

    public String get_src() {
        return src;
    }

    public String get_text() {
        return text;
    }
}
