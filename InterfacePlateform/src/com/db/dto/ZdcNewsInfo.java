package com.db.dto;

import java.io.Serializable;

public class ZdcNewsInfo implements Serializable {
    private String title = null;
    private String author = null;
    private String description = null;
    private String pubDate = null;
    private String content = null;
    private String url = null;
    private String type = null;

    public ZdcNewsInfo(String title, String author, String description,
	    String pubDate, String url, String type) {
	super();
	this.title = title;
	this.author = author;
	this.description = description;
	this.pubDate = pubDate;
	this.url = url;
	this.type = type;
    }

    public ZdcNewsInfo(String title, String author, String description,
	    String pubDate, String content, String url, String type) {
	super();
	this.title = title;
	this.author = author;
	this.description = description;
	this.pubDate = pubDate;
	this.content = content;
	this.url = url;
	this.type = type;
    }

    public String getUrl() {
	return url;
    }

    public void setUrl(String url) {
	this.url = url;
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    public String getAuthor() {
	return author;
    }

    public void setAuthor(String author) {
	this.author = author;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public String getPubDate() {
	return pubDate;
    }

    public void setPubDate(String pubDate) {
	this.pubDate = pubDate;
    }

    public String getContent() {
	return content;
    }

    public void setContent(String content) {
	this.content = content;
    }

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

}
