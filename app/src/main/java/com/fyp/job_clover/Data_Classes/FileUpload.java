package com.fyp.job_clover.Data_Classes;

public class FileUpload {

    public String name;
    public String url;
    public  String sek_id;
    public String key;




    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public FileUpload() {
    }



    public FileUpload(String name, String url,String sek_id) {
        this.name = name;
        this.url = url;
        this.sek_id = sek_id;

    }

    public String getSek_id() {
        return sek_id;
    }

    public void setSek_id(String sek_id) {
        this.sek_id = sek_id;
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
