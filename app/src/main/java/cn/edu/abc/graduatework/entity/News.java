package cn.edu.abc.graduatework.entity;


public class News {

    private String id;

    private String imgUrl;

    private String title;

    private String authorName;

    private String schoolId;

    private String createTime;

    private int commentCount;

    private String isCollection;
    private String type;

    private String content;
    public News() {
    }

    public News(String title, String imgUrl, String date, int commentCount, String content, String isCollection, String type) {
        this.title = title;
        this.imgUrl = imgUrl;
        this.createTime = date;
        this.commentCount = commentCount;
        this.content = content;
        this.isCollection = isCollection;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getIsCollection() {
        return isCollection;
    }

    public void setIsCollection(String isCollection) {
        this.isCollection = isCollection;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
