package com.fyp.job_clover.Data_Classes;

public class Emp_Post_Data {
    public String job_title,company_name,company_email,company_city,company_address,
            company_phone,req_education,company_position,salary_from,salary_to,job_type,description,specific_key;

    public Emp_Post_Data() {
    }

    public Emp_Post_Data(String job_title, String company_name, String company_email, String company_city, String company_address,
                         String company_phone, String req_education,
                         String company_position, String salary_from, String salary_to, String job_type, String description) {
        this.job_title = job_title;
        this.company_name = company_name;
        this.company_email = company_email;
        this.company_city = company_city;
        this.company_address = company_address;
        this.company_phone = company_phone;
        this.req_education = req_education;
        this.company_position = company_position;
        this.salary_from = salary_from;
        this.salary_to = salary_to;
        this.job_type = job_type;
        this.description = description;
    }

    public String getSpecific_key() {
        return specific_key;
    }

    public void setSpecific_key(String specific_key) {
        this.specific_key = specific_key;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCompany_email() {
        return company_email;
    }

    public void setCompany_email(String company_email) {
        this.company_email = company_email;
    }

    public String getCompany_city() {
        return company_city;
    }

    public void setCompany_city(String company_city) {
        this.company_city = company_city;
    }

    public String getCompany_address() {
        return company_address;
    }

    public void setCompany_address(String company_address) {
        this.company_address = company_address;
    }

    public String getCompany_phone() {
        return company_phone;
    }

    public void setCompany_phone(String company_phone) {
        this.company_phone = company_phone;
    }

    public String getReq_education() {
        return req_education;
    }

    public void setReq_education(String req_education) {
        this.req_education = req_education;
    }

    public String getCompany_position() {
        return company_position;
    }

    public void setCompany_position(String company_position) {
        this.company_position = company_position;
    }

    public String getSalary_from() {
        return salary_from;
    }

    public void setSalary_from(String salary_from) {
        this.salary_from = salary_from;
    }

    public String getSalary_to() {
        return salary_to;
    }

    public void setSalary_to(String salary_to) {
        this.salary_to = salary_to;
    }

    public String getJob_type() {
        return job_type;
    }

    public void setJob_type(String job_type) {
        this.job_type = job_type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
