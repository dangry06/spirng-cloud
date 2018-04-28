package com.aspire.imp.web.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aspire.imp.aop.annotation.Auth;
import com.aspire.imp.common.contanst.ApiResult;
import com.aspire.imp.common.contanst.RetCode;
import com.aspire.imp.common.util.DateTools;
import com.aspire.imp.common.util.fileupload.FileSystemStorageService;
import com.aspire.imp.web.entity.SysUser;
import com.aspire.imp.web.service.SysUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping(value = "/rest/user")
public class SysUserRestController {
	private final static Logger log = LoggerFactory.getLogger(SysUserRestController.class);
	
	@Autowired
	private SysUserService userService;
	@Autowired
	private FileSystemStorageService fileSystemStorageService;
	
	@Auth
	@RequestMapping("/page")
	public ApiResult pageUser(
		@RequestParam(required = false) Integer pageNum, 
		@RequestParam(required = false) Integer pageSize) {
		pageNum = pageNum == null ? 1 : pageNum;
		pageSize = pageSize == null ? 10 : pageSize;
		PageHelper.startPage(pageNum, pageSize);
		List<SysUser> userList = this.userService.query();
		PageInfo<SysUser> pageInfo = new PageInfo<SysUser>(userList);
		
		return new ApiResult(pageInfo);
	}
	
	/**
	 * 查询用户信息
	 */
	@Auth
	@RequestMapping(value = "/get/{id:\\d+}")
	public ApiResult getUser(@PathVariable Long id) {
		SysUser user = this.userService.getById(id);
		if (user == null) {
			return new ApiResult(RetCode.USER_NOTEXIST, "未查询到用户信息");
		}
		return new ApiResult(user);
	}

	@RequestMapping(value = "/register")
	public ApiResult register(
			@RequestParam String loginName, 
			@RequestParam String loginPass, 
			@RequestParam(required = false)String nickname, 
			@RequestParam(required = false)String truename, 
			@RequestParam Integer gender, 
			@RequestParam(required = false)String mobile, 
			@RequestParam(required = false)String email, 
			@RequestParam(required = false)String intro) {
		SysUser dbUser = this.userService.getByLoginName(loginName.trim());
		if (dbUser != null) {
			return new ApiResult(RetCode.USER_EXISTED, "用户已存在，请重新设置登录账号");
		}
		
		SysUser user = this.userService.add(loginName.trim(), loginPass.trim(), nickname, truename, gender, mobile, email, intro);
		return new ApiResult(user);
	}
	
	@Auth
	@RequestMapping(value = "/update")
	public ApiResult updateUser(
			@RequestParam(required = false)String nickname, 
			@RequestParam(required = false)String truename, 
			@RequestParam(required = false)Integer gender, 
			@RequestParam(required = false)String mobile, 
			@RequestParam(required = false)String email, 
			@RequestParam(required = false)String intro,
			HttpServletRequest request) {
		SysUser user = (SysUser)request.getSession().getAttribute(request.getRequestedSessionId());
		user = this.userService.update(user.getId(), nickname, truename, gender, mobile, email, intro);
		request.getSession().setAttribute(request.getRequestedSessionId(), user);
		return new ApiResult(user);
	}
	
	@Auth
	@RequestMapping(value = "/modifyPass")
	public ApiResult modifyPassword(
			@RequestParam String oldPass, 
			@RequestParam String newPass,
			HttpServletRequest request) {
		SysUser user = (SysUser)request.getSession().getAttribute(request.getRequestedSessionId());
		this.userService.modifyPassword(user.getId(), oldPass, newPass);
		return new ApiResult(RetCode.SUCCESS, "");
	}
	
	@Auth
    @PostMapping("/upload")
    public ApiResult handleFileUpload(@RequestParam("file") MultipartFile file, 
    		@RequestParam(required = false) Integer picSource,
    		HttpServletRequest request,
    		HttpServletResponse response) {
		SysUser user = (SysUser)request.getSession().getAttribute(request.getRequestedSessionId());
		Long userId = user.getId();
		String subdir = new StringBuffer().append(userId)
				.append("/")
				.append(DateTools.getDateTimeString(new Date(), "yyyyMMdd"))
				.toString();
    	fileSystemStorageService.store(subdir, file);
    	
    	String url = "/rest/file/" + subdir + "/" + file.getOriginalFilename();
    	
    	//更新用户头像或背景图片
    	this.userService.updateUserPic(userId, url, picSource);
    	
    	return new ApiResult(url);
    }
	
}
