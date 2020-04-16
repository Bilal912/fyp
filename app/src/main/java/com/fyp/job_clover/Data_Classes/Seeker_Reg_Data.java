package com.fyp.job_clover.Data_Classes;

public class Seeker_Reg_Data {
    public String seeker_name,seeker_email,seeker_qualification,seeker_address,seeker_phone,seeker_password,seeker_gender;

    public Seeker_Reg_Data() {
    }

    public Seeker_Reg_Data(String seeker_name, String seeker_email, String seeker_qualification,
                           String seeker_address, String seeker_phone, String seeker_password, String seeker_gender) {
        this.seeker_name = seeker_name;
        this.seeker_email = seeker_email;
        this.seeker_qualification = seeker_qualification;
        this.seeker_address = seeker_address;
        this.seeker_phone = seeker_phone;
        this.seeker_password = seeker_password;
        this.seeker_gender = seeker_gender;
    }

    public String getSeeker_name() {
        return seeker_name;
    }

    public void setSeeker_name(String seeker_name) {
        this.seeker_name = seeker_name;
    }

    public String getSeeker_email() {
        return seeker_email;
    }

    public void setSeeker_email(String seeker_email) {
        this.seeker_email = seeker_email;
    }

    public String getSeeker_qualification() {
        return seeker_qualification;
    }

    public void setSeeker_qualification(String seeker_qualification) {
        this.seeker_qualification = seeker_qualification;
    }

    public String getSeeker_address() {
        return seeker_address;
    }

    public void setSeeker_address(String seeker_address) {
        this.seeker_address = seeker_address;
    }

    public String getSeeker_phone() {
        return seeker_phone;
    }

    public void setSeeker_phone(String seeker_phone) {
        this.seeker_phone = seeker_phone;
    }

    public String getSeeker_password() {
        return seeker_password;
    }

    public void setSeeker_password(String seeker_password) {
        this.seeker_password = seeker_password;
    }

    public String getSeeker_gender() {
        return seeker_gender;
    }

    public void setSeeker_gender(String seeker_gender) {
        this.seeker_gender = seeker_gender;
    }
}
