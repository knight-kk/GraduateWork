package cn.edu.abc.graduatework.entity;


public class School {

    private String mName;
    private String schoolId;

    public School(String name) {
        mName = name;
    }

    public School(String name, String schoolId) {
        mName = name;
        this.schoolId = schoolId;
    }

    public School() {
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }
}
