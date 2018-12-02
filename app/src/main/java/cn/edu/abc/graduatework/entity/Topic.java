package cn.edu.abc.graduatework.entity;


import java.io.Serializable;

public class Topic implements Serializable {
    private String id;

    private String title;

    private String content;

    private String imgUrl;

    private String userId;

    private int isNew=0;

    private int isFollow;// 0 未关注，1 关注

    private String author;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? "" : id.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? "" : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? "" : content.trim();
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? "" : imgUrl.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? "" : userId.trim();
    }

    public Integer getIsNew() {
        return isNew;
    }

    public void setIsNew(Integer isNew) {
        this.isNew = isNew;
    }

    public int getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(int isFollow) {
        this.isFollow = isFollow;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author == null ? "" : author.trim();
    }
}
