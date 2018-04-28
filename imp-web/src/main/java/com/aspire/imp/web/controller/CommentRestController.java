package com.aspire.imp.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aspire.imp.aop.annotation.Auth;
import com.aspire.imp.common.contanst.ApiResult;
import com.aspire.imp.web.entity.Comment;
import com.aspire.imp.web.entity.SysUser;
import com.aspire.imp.web.service.CommentService;

@RestController
@RequestMapping(value = "/rest/comment")
@Auth
public class CommentRestController {
	@Autowired
	private CommentService commentService;
	
	@Auth
	@RequestMapping("/add")
	public ApiResult addComment(@RequestParam Long postId,
			@RequestParam String content,
			@RequestParam(required = false) Long commentParent,
			@RequestParam(required = false) Long commentRoot,
			HttpServletRequest request) {
		SysUser user = (SysUser)request.getSession().getAttribute(request.getRequestedSessionId());
		Comment comment = this.commentService.addComment(postId, user, content, commentParent, commentRoot);
		return new ApiResult(comment);
	}
}
