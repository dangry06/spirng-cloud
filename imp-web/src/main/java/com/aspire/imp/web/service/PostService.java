package com.aspire.imp.web.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.aspire.imp.common.contanst.PostStatusEnum;
import com.aspire.imp.common.contanst.RetCode;
import com.aspire.imp.common.contanst.StatusEnum;
import com.aspire.imp.common.exception.BusinessException;
import com.aspire.imp.web.dao.CommentMapper;
import com.aspire.imp.web.dao.PostMapper;
import com.aspire.imp.web.dao.PostTermMapper;
import com.aspire.imp.web.dao.SysUserMapper;
import com.aspire.imp.web.dao.TermMapper;
import com.aspire.imp.web.entity.Comment;
import com.aspire.imp.web.entity.Post;
import com.aspire.imp.web.entity.PostTerm;
import com.aspire.imp.web.entity.SysUser;
import com.aspire.imp.web.entity.Term;
import com.google.common.base.Strings;

@Service
public class PostService {
	@Autowired
	private PostMapper postMapper;
	@Autowired
	private CommentMapper commentMapper;
	@Autowired
	private PostTermMapper postTermMapper;
	@Autowired
	private SysUserMapper userMapper;
	@Autowired
	private TermMapper termMapper;
	
	public List<Post> query() {
		List<Post> postList = this.postMapper.query();
		if (postList == null || postList.isEmpty()) {
			return postList;
		}
		
		for (Post post : postList) {
			post = getExtraPost(post);
		}
		
		return postList;
	}

	public List<Post> queryByTerm(Integer termId) {
		List<Post> postList = this.postMapper.queryByTerm(termId);
		if (postList == null || postList.isEmpty()) {
			return postList;
		}
		
		for (Post post : postList) {
			post = getExtraPost(post);
		}
		
		return postList;
	}
	
	public List<Post> queryByAuthor(Long authorId, Integer draftFlag) {
		List<Post> postList = null;
		if (draftFlag == null) {
			//查询已发表的文章
			postList = this.postMapper.queryByAuthor(authorId, PostStatusEnum.PUBLISH.getValue());
		} else {
			//查询草稿
			postList = this.postMapper.queryByAuthor(authorId, PostStatusEnum.DRAFT.getValue());
		}
		
		if (postList == null || postList.isEmpty()) {
			return postList;
		} else {
			for (Post post : postList) {
				post = getExtraPost(post);
			}
		}
		return postList;
	}
	
	public Post get(Long id) {
		Post post = this.postMapper.get(id);
		if (post != null) {
			post = getExtraPost(post);
			post = getCommentPost(post);
		}
		return post;
	}
	
	private Post getCommentPost(Post post) {
		if (post == null) {
			return null;
		}
		
		long postId = post.getId();
		List<Comment> commentList = this.commentMapper.queryParentCommentByPostId(postId);
		if (commentList == null || commentList.isEmpty()) {
			return post;
		}
		for (Comment comment : commentList) {
			Long commentRootId = comment.getId();
			List<Comment> subCommentList = this.commentMapper.querySubCommentByPostId(postId, commentRootId);
			comment.setSubCommentList(subCommentList);
		}
		post.setCommentList(commentList);
		
		return post;
	}
	
	private Post getExtraPost(Post post) {
		if (post == null) {
			return null;
		}
		
		Long authorId = post.getAuthorId();
		SysUser user = this.userMapper.getById(authorId);
		if (user != null) {
			post.setAuthorNickname(user.getNickname());
		}
		
		List<Term> termList = this.postTermMapper.queryTermByPostId(post.getId());
		post.setTermList(termList);
		
		return post;
	}

	public List<Term> queryAllTerm() {
		return this.termMapper.query();
	}

	@Transactional
	public Post addPost(Long authorId, String title, String excerpt, String content, 
			Integer commentStatus, String headerImage, Integer termId, 
			PostStatusEnum postStatus, String tmpPid) {
		Date now = new Date();
		Post post = new Post();
		post.setAuthorId(authorId);
		post.setCommentStatus(StatusEnum.ENABLED.getValue());
		post.setContent(content);
		post.setCreateTime(now);
		post.setExcerpt(excerpt);
		post.setHeaderImage(headerImage);
		post.setPostParent(0L);
		post.setStatus(postStatus.getValue());
		post.setTitle(title);
		post.setUpdateTime(now);
		this.postMapper.add(post);
		
		Long postId = post.getId();
		PostTerm pt = new PostTerm();
		pt.setPostId(postId);
		pt.setTermId(termId == null ? 1 : termId);
		this.postTermMapper.add(pt);
		
		if (!Strings.isNullOrEmpty(tmpPid)) {
			Long oldPostParent = Long.valueOf(tmpPid);
			this.postMapper.updateSubPost(postId, oldPostParent);
		}
		
		return this.get(postId);
	}

	@Transactional
	public void addSubPost(MultipartFile file, String alias, String url, Long authorId, String tmpPid) {
		Date now = new Date();
		Post post = new Post();
		post.setAuthorId(authorId);
		post.setCommentStatus(StatusEnum.ENABLED.getValue());
		post.setContent(url);
		post.setCreateTime(now);
		post.setExcerpt(null);
		post.setHeaderImage(null);
		post.setPostParent(Long.valueOf(tmpPid));
		post.setStatus(PostStatusEnum.INHERIT.getValue());
		if (Strings.isNullOrEmpty(alias)) {
			alias = file.getOriginalFilename();
		}
		post.setTitle(alias);
		post.setUpdateTime(now);
		post.setPostMimeType(file.getContentType());
		post.setPostName(file.getOriginalFilename());
		this.postMapper.add(post);
	}
	
	@Transactional
	public Post editPost(Long id, String title, String excerpt, String content, Integer commentStatus,
			String headerImage, Integer termId, PostStatusEnum postStatus) {
		Post post = this.postMapper.get(id);
		if (post == null) {
			throw new BusinessException(RetCode.POST_NOTEXIST, "文章信息不存在，更新失败");
		}
		
		post.setTitle(title);
		post.setExcerpt(excerpt);
		post.setContent(content);
		post.setCommentStatus(commentStatus);
		post.setHeaderImage(headerImage);
		post.setStatus(postStatus.getValue());
		post.setUpdateTime(new Date());
		this.postMapper.update(post);
		
		this.postTermMapper.deleteByPostId(id);
		PostTerm pt = new PostTerm();
		pt.setPostId(id);
		pt.setTermId(termId == null ? 1 : termId);
		this.postTermMapper.add(pt);
		
		return this.get(id);
	}

	@Transactional
	public Post updatePostStatus(Long postId, Long authorId, PostStatusEnum postStatus) {
		Post post = this.postMapper.get(postId);
		if (post == null) {
			throw new BusinessException(RetCode.POST_NOTEXIST, "文章信息不存在，操作失败");
		}
		if (post.getAuthorId().longValue() != authorId) {
			throw new BusinessException(RetCode.POST_NORIGHT, "您没有权限操作别人的文章");
		}
		this.postMapper.updatePostStatus(postId, postStatus.getValue());
		return this.get(postId);
	}

	@Transactional
	public Long thumbsupPost(Long postId, Long authorId) {
		synchronized(this) {
			this.postMapper.updatePostThumbsupCount(postId);
		}
		return this.postMapper.getThumbsupCountById(postId);
	}

}
