package com.seeksolution.projectdemo.Model;

public class VideoModel {

    public String id;
    public String vedio_url;
    public String vedio_banner;
    public String vedio_description;
    public String year;
    public String rating;
    public String category;

    public VideoModel(String id, String vedio_url, String vedio_banner, String vedio_description, String year, String rating, String category) {
        this.id = id;
        this.vedio_url = vedio_url;
        this.vedio_banner = vedio_banner;
        this.vedio_description = vedio_description;
        this.year = year;
        this.rating = rating;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public String getVedio_url() {
        return vedio_url;
    }

    public String getVedio_banner() {
        return vedio_banner;
    }

    public String getVedio_description() {
        return vedio_description;
    }

    public String getYear() {
        return year;
    }

    public String getRating() {
        return rating;
    }

    public String getCategory() {
        return category;
    }
}
