package com.aspire.imp.web.entity;

import java.util.Date;
import java.util.List;

public class Post {
	private Long id;
	private Long authorId;
	private String content;
	private String title;
	private String excerpt;
	private Integer status;
	private Integer commentStatus;
	private Date createTime;
	private Date updateTime;
	private String headerImage;
	private String postName;
	private Long postParent;
	private String postMimeType;
	private Long commentCount;
	private Long thumbsupCount;
	
	private String authorNickname;
	private List<Term> termList;
	private List<Comment> commentList;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getAuthorId() {
		return authorId;
	}
	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getExcerpt() {
		return excerpt;
	}
	public void setExcerpt(String excerpt) {
		this.excerpt = excerpt;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getCommentStatus() {
		return commentStatus;
	}
	public void setCommentStatus(Integer commentStatus) {
		this.commentStatus = commentStatus;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getHeaderImage() {
		return headerImage;
	}
	public void setHeaderImage(String headerImage) {
		this.headerImage = headerImage;
	}
	public String getPostName() {
		return postName;
	}
	public void setPostName(String postName) {
		this.postName = postName;
	}
	public Long getPostParent() {
		return postParent;
	}
	public void setPostParent(Long postParent) {
		this.postParent = postParent;
	}
	public String getPostMimeType() {
		return postMimeType;
	}
	public void setPostMimeType(String postMimeType) {
		this.postMimeType = postMimeType;
	}
	public Long getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(Long commentCount) {
		this.commentCount = commentCount;
	}
	public Long getThumbsupCount() {
		return thumbsupCount;
	}
	public void setThumbsupCount(Long thumbsupCount) {
		this.thumbsupCount = thumbsupCount;
	}
	public String getAuthorNickname() {
		return authorNickname;
	}
	public void setAuthorNickname(String authorNickname) {
		this.authorNickname = authorNickname;
	}
	public List<Term> getTermList() {
		return termList;
	}
	public void setTermList(List<Term> termList) {
		this.termList = termList;
	}
	public List<Comment> getCommentList() {
		return commentList;
	}
	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}
}
