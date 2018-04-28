package com.aspire.imp.web.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aspire.imp.common.contanst.RetCode;
import com.aspire.imp.common.contanst.StatusEnum;
import com.aspire.imp.common.exception.BusinessException;
import com.aspire.imp.common.util.MessageDigestUtils;
import com.aspire.imp.web.dao.SysUserMapper;
import com.aspire.imp.web.entity.SysUser;
import com.google.common.base.Strings;

@Service
public class SysUserService {
	@Autowired
	private SysUserMapper userMapper;

	public SysUser login(String loginName, String loginPass) {
		return this.userMapper.login(loginName, MessageDigestUtils.md5Digest(loginPass));
	}
	
	@Transactional
	public SysUser add(String loginName, String loginPass, String nickname, String truename, 
			Integer gender, String mobile, String email, String intro) {
		Date now = new Date();
		SysUser user = new SysUser();
		user.setLoginName(loginName);
		user.setLoginPass(MessageDigestUtils.md5Digest(loginPass));
		if (Strings.isNullOrEmpty(nickname)) {
			if (!Strings.isNullOrEmpty(truename)) {
				user.setNickname(truename);
			} else {
				user.setNickname(loginName);
			}
		} else {
			user.setNickname(nickname);
		}
		user.setTruename(truename);
		user.setGender(gender);
		user.setMobile(mobile);
		user.setEmail(email);
		user.setIntro(intro);
		user.setStatus(StatusEnum.ENABLED.getValue());
		user.setCreateTime(now);
		user.setUpdateTime(now);
		this.userMapper.add(user);
		
		return this.getById(user.getId());
	}

	@Transactional
	public SysUser update(Long id, String nickname, String truename, Integer gender, String mobile, String email, String intro) {
		SysUser user = this.userMapper.getById(id);
		if (user != null) {
			if (!Strings.isNullOrEmpty(nickname)) {
				user.setNickname(nickname);
			}
			if (!Strings.isNullOrEmpty(truename)) {
				user.setTruename(truename);
			}
			if (gender != null) {
				user.setGender(gender);
			}
			if (!Strings.isNullOrEmpty(mobile)) {
				user.setMobile(mobile);
			}
			if (!Strings.isNullOrEmpty(email)) {
				user.setEmail(email);
			}
			if (!Strings.isNullOrEmpty(intro)) {
				user.setIntro(intro);
			}
			user.setUpdateTime(new Date());
			this.userMapper.update(user);
			return user;
		} else {
			throw new BusinessException(RetCode.USER_NOTEXIST, "用户信息不存在，更新失败");
		}
	}

	@Transactional
	public void updateUserPic(Long id, String url, Integer picSource) {
		SysUser user = this.userMapper.getById(id);
		if (user != null) {
			if (picSource == null) {
				user.setAvatarUrl(url);
			} else {
				user.setPicUrl(url);
			}
			user.setUpdateTime(new Date());
			this.userMapper.updatePicUrl(user);
		} else {
			throw new BusinessException(RetCode.USER_NOTEXIST, "用户信息不存在，更新失败");
		}
	}
	
	@Transactional
	public void modifyPassword(Long id, String oldPass, String newPass) {
		String dbLoginPass = this.userMapper.getLoginPassById(id);
		if (Strings.isNullOrEmpty(dbLoginPass)) {
			throw new BusinessException(RetCode.USER_NOTEXIST, "用户信息不存在，操作失败");
		}
		if (!MessageDigestUtils.md5Digest(oldPass).equals(dbLoginPass)) {
			throw new BusinessException(RetCode.PASSWORD_ERROR, "原密码错误，操作失败");
		}
		this.userMapper.updateLoginPassById(id, MessageDigestUtils.md5Digest(newPass));
	}
	
	@Transactional
	public int delete(Long id) {
		return this.userMapper.delete(id);
	}

	public SysUser getById(Long id) {
		return this.userMapper.getById(id);
	}

	public List<SysUser> query() {
		return this.userMapper.query();
	}

	public SysUser getByLoginName(String loginName) {
		return this.userMapper.getByLoginName(loginName);
	}

}
