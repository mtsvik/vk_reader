package model;

import java.util.ArrayList;

/**
 * Author: Mikhail Tsvik (tsvik@me.com)
 * Date: 17.06.14
 */

public class Attachments {

    private ArrayList<Video> videos;
    private ArrayList<Audio> audios;
    private ArrayList<Photo> photos;
    private Link link;

    public Attachments(ArrayList<Video> videos, ArrayList<Audio> audios, ArrayList<Photo> photos, Link link) {
        this.videos = videos;
        this.audios = audios;
        this.photos = photos;
        this.link = link;
    }

    public ArrayList<Video> getVideos() {
        return videos;
    }

    public ArrayList<Audio> getAudios() {
        return audios;
    }

    public ArrayList<Photo> getPhotos() {
        return photos;
    }

    public Link getLink() {
        return link;
    }

    static class Link {
        private String title;
        private String URL;
        private String description;

        public Link(String title, String URL, String description) {
            this.title = title;
            this.URL = URL;
            this.description = description;
        }

        public String getTitle() {
            return title;
        }

        public String getURL() {
            return URL;
        }

        public String getDescription() {
            return description;
        }
    }

    static class Video {
       private int vid;
       private String title;
       private int duration;
       private String image_src;

        public Video(int vid, String title, int duration, String image_src) {
            this.vid = vid;
            this.title = title;
            this.duration = duration;
            this.image_src = image_src;
        }

        public int getVid() {
            return vid;
        }

        public String getTitle() {
            return title;
        }

        public int getDuration() {
            return duration;
        }

        public String getImage_src() {
            return image_src;
        }
    }

    static class Audio {
        private String artist;
        private String title;
        private String url;
        private int duraton;
        private int aid;

        Audio(String artist, String title, String url, int duraton, int aid) {
            this.artist = artist;
            this.title = title;
            this.url = url;
            this.duraton = duraton;
            this.aid = aid;
        }

        public String getArtist() {
            return artist;
        }

        public String getTitle() {
            return title;
        }

        public String getUrl() {
            return url;
        }

        public int getDuraton() {
            return duraton;
        }

        public int getAid() {
            return aid;
        }
    }
}
