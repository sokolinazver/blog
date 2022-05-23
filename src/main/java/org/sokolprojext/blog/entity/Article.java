package org.sokolprojext.blog.entity;

import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;

public class Article extends AbstractEntity<Long>{

    private String title;
    private String url;
    private String logo;
    private String desc;
    private String content;
    private int idCategory;
    private Timestamp created;
    private long views;

    public String getArticleLink(){
        return "/article/"+getId()+url;
    }

    public String getShortTitle(){
        if(StringUtils.length(title) > 20){
            return title.substring(0,17)+"...";
        } else {
            return title;
        }
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    private int comments;
}
