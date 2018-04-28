package com.aspire.imp.web.entity;

public class SysRole {
	private Integer id;
	private String roleName;
	private String roleDescs;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleDescs() {
		return roleDescs;
	}
	public void setRoleDescs(String roleDescs) {
		this.roleDescs = roleDescs;
	}
}
