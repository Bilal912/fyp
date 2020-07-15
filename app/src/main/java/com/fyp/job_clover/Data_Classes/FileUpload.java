package com.fyp.job_clover.Data_Classes;

public class FileUpload {

    public String name;
    public String url;
    public String key;



    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public FileUpload() {
    }

    public FileUpload(String name, String url) {
        this.name = name;
        this.url = url;
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
