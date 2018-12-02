package cn.edu.abc.graduatework.entity;


import java.io.Serializable;

public class Graduate implements Serializable {
    private String id;

    private String imgUrl;

    private String schoolId;

    private String choolName;

    private String goodNumber;

    private String major;

    private String company;

    private String job;

    private String graduateInfo;

    private String userId;

    private String userName;


    public Graduate(String name, String imgUrl, String school) {
        this.userName = name;
        this.imgUrl = imgUrl;
        this.choolName = school;
    }

    public Graduate(String name, String imgUrl, String school, String graduateInfo) {
        this.userName = name;
        this.imgUrl = imgUrl;
        this.choolName = school;
        this.graduateInfo = graduateInfo;
    }

    public Graduate(String name, String imgUrl, String major, String company, String jobName) {
        this.userName = name;
        this.imgUrl = imgUrl;
        this.major = major;
        this.company = company;
        this.job = jobName;
    }

    public Graduate() {
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getChoolName() {
        return choolName;
    }

    public void setChoolName(String choolName) {
        this.choolName = choolName;
    }

    public String getGoodNumber() {
        return goodNumber;
    }

    public void setGoodNumber(String goodNumber) {
        this.goodNumber = goodNumber;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getGraduateInfo() {
        return graduateInfo;
    }

    public void setGraduateInfo(String graduateInfo) {
        this.graduateInfo = graduateInfo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
