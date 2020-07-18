package com.fyp.job_clover.Data_Classes;

public class Fav_CV {
    public String name;
    public String url;
    public String key;


    public Fav_CV() {
    }

    public Fav_CV(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
