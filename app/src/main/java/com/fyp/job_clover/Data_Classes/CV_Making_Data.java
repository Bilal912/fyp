package com.fyp.job_clover.Data_Classes;

public class CV_Making_Data {
    public String cv_job_title,cv_name,cv_email,cv_phone,cv_req_education,cv_city,cv_address,
            cv_skills,cv_experience;

    public CV_Making_Data() {
    }

    public CV_Making_Data(String cv_job_title, String cv_name, String cv_email, String cv_phone,
                          String cv_req_education, String cv_city, String cv_address, String cv_skills, String cv_experience) {
        this.cv_job_title = cv_job_title;
        this.cv_name = cv_name;
        this.cv_email = cv_email;
        this.cv_phone = cv_phone;
        this.cv_req_education = cv_req_education;
        this.cv_city = cv_city;
        this.cv_address = cv_address;
        this.cv_skills = cv_skills;
        this.cv_experience = cv_experience;
    }

    public String getCv_job_title() {
        return cv_job_title;
    }

    public void setCv_job_title(String cv_job_title) {
        this.cv_job_title = cv_job_title;
    }

    public String getCv_name() {
        return cv_name;
    }

    public void setCv_name(String cv_name) {
        this.cv_name = cv_name;
    }

    public String getCv_email() {
        return cv_email;
    }

    public void setCv_email(String cv_email) {
        this.cv_email = cv_email;
    }

    public String getCv_phone() {
        return cv_phone;
    }

    public void setCv_phone(String cv_phone) {
        this.cv_phone = cv_phone;
    }

    public String getCv_req_education() {
        return cv_req_education;
    }

    public void setCv_req_education(String cv_req_education) {
        this.cv_req_education = cv_req_education;
    }

    public String getCv_city() {
        return cv_city;
    }

    public void setCv_city(String cv_city) {
        this.cv_city = cv_city;
    }

    public String getCv_address() {
        return cv_address;
    }

    public void setCv_address(String cv_address) {
        this.cv_address = cv_address;
    }

    public String getCv_skills() {
        return cv_skills;
    }

    public void setCv_skills(String cv_skills) {
        this.cv_skills = cv_skills;
    }

    public String getCv_experience() {
        return cv_experience;
    }

    public void setCv_experience(String cv_experience) {
        this.cv_experience = cv_experience;
    }
}
