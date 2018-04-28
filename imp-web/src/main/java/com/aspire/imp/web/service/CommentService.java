package com.aspire.imp.web.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aspire.imp.common.contanst.StatusEnum;
import com.aspire.imp.web.dao.CommentMapper;
import com.aspire.imp.web.dao.PostMapper;
import com.aspire.imp.web.entity.Comment;
import com.aspire.imp.web.entity.SysUser;

@Service
public class CommentService {
	@Autowired
	private CommentMapper commentMapper;
	@Autowired
	private PostMapper postMapper;
	
	@Transactional
	public Comment addComment(Long postId, SysUser user, String content, Long commentParent, Long commentRoot) {
		Comment com = new Comment();
		com.setApprovedFlag(StatusEnum.ENABLED.getValue());
		com.setAuthorId(user.getId());
		com.setAuthorNickname(user.getNickname());
		if (commentParent == null) {
			commentParent = 0L;
		}
		com.setCommentParent(commentParent);
		if (commentRoot == null) {
			commentRoot = 0L;
		}
		com.setCommentRoot(commentRoot);
		com.setContent(content);
		com.setCreateTime(new Date());
		com.setPostId(postId);
		
		this.commentMapper.add(com);
		
		synchronized (this) {
			this.postMapper.updateCommentCountById(postId);
		}
		return com;
	}
}
