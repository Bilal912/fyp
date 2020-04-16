package com.fyp.job_clover.Data_Classes;

public class Employer_Reg_Data {
    public String employer_name,employer_email,employer_city,employer_address,employer_password;

    public Employer_Reg_Data() {
    }

    public Employer_Reg_Data(String employer_name, String employer_email,
                             String employer_city, String employer_address, String employer_password) {
        this.employer_name = employer_name;
        this.employer_email = employer_email;
        this.employer_city = employer_city;
        this.employer_address = employer_address;
        this.employer_password = employer_password;
    }

    public String getEmployer_name() {
        return employer_name;
    }

    public void setEmployer_name(String employer_name) {
        this.employer_name = employer_name;
    }

    public String getEmployer_email() {
        return employer_email;
    }

    public void setEmployer_email(String employer_email) {
        this.employer_email = employer_email;
    }

    public String getEmployer_city() {
        return employer_city;
    }

    public void setEmployer_city(String employer_city) {
        this.employer_city = employer_city;
    }

    public String getEmployer_address() {
        return employer_address;
    }

    public void setEmployer_address(String employer_address) {
        this.employer_address = employer_address;
    }

    public String getEmployer_password() {
        return employer_password;
    }

    public void setEmployer_password(String employer_password) {
        this.employer_password = employer_password;
    }
}
