package com.fyp.job_clover.Data_Classes;

public class AppliedJobs {
   public String title,jobtype,namecity,salary,description,position,emp_id,dkey;

    public AppliedJobs() {
    }



    public AppliedJobs(String title, String jobtype, String namecity,
                       String salary, String description, String position, String emp_id )
    {
        this.title = title;
        this.jobtype = jobtype;
        this.namecity = namecity;
        this.salary = salary;
        this.description = description;
        this.position = position;
        this.emp_id = emp_id;

    }


    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getJobtype() {
        return jobtype;
    }

    public void setJobtype(String jobtype) {
        this.jobtype = jobtype;
    }

    public String getNamecity() {
        return namecity;
    }

    public void setNamecity(String namecity) {
        this.namecity = namecity;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDkey() {
        return dkey;
    }

    public void setDkey(String dkey) {
        this.dkey = dkey;
    }
}
