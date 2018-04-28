package com.aspire.imp.web.entity;

import java.util.Date;
import java.util.List;

public class Comment {
	private Long id;
	private Long postId;
	private Long authorId;
	private String authorNickname;
	private Date createTime;
	private String content;
	private Integer approvedFlag;
	private Long commentParent;
	private Long commentRoot;
	
	private List<Comment> subCommentList;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPostId() {
		return postId;
	}
	public void setPostId(Long postId) {
		this.postId = postId;
	}
	public Long getAuthorId() {
		return authorId;
	}
	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}
	public String getAuthorNickname() {
		return authorNickname;
	}
	public void setAuthorNickname(String authorNickname) {
		this.authorNickname = authorNickname;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getApprovedFlag() {
		return approvedFlag;
	}
	public void setApprovedFlag(Integer approvedFlag) {
		this.approvedFlag = approvedFlag;
	}
	public Long getCommentParent() {
		return commentParent;
	}
	public void setCommentParent(Long commentParent) {
		this.commentParent = commentParent;
	}
	public Long getCommentRoot() {
		return commentRoot;
	}
	public void setCommentRoot(Long commentRoot) {
		this.commentRoot = commentRoot;
	}
	public List<Comment> getSubCommentList() {
		return subCommentList;
	}
	public void setSubCommentList(List<Comment> subCommentList) {
		this.subCommentList = subCommentList;
	}
}
