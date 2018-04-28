package com.aspire.imp.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.aspire.imp.web.entity.SysUser;

@Mapper
public interface SysUserMapper {
	/**
	 * 新增
	 */
	String INSERT_COLUMN = "login_name, login_pass, nickname, truename, gender, mobile, email, status, intro, create_time, update_time";
	String INSERT_VALUE = "#{loginName}, #{loginPass}, #{nickname}, #{truename}, #{gender}, #{mobile}, #{email}, #{status}, #{intro}, #{createTime}, #{updateTime}";
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	@Insert("insert into sys_user (" + INSERT_COLUMN + ") values (" + INSERT_VALUE + ")")
	int add(SysUser user);

	/**
	 * 更新
	 */
	String UPDATE_COLUMN = "nickname = #{nickname}, truename = #{truename}, gender = #{gender}, mobile = #{mobile}, "
			+ "email = #{email}, intro = #{intro}, update_time = #{updateTime}";
	@Update("update sys_user set " + UPDATE_COLUMN + " where id = #{id}")
	int update(SysUser user);

	/**
	 * 更新图片地址
	 */
	String UPDATE_PIC_COLUMN = "avatar_url = #{avatarUrl}, pic_url = #{picUrl} ";
	@Update("update sys_user set " + UPDATE_PIC_COLUMN + " where id = #{id}")
	int updatePicUrl(SysUser user);
	
	/**
	 * 删除
	 */
	@Delete("delete from sys_user where id = #{id}")
	int delete(Long id);

	/**
	 * 查询详情
	 */
	String SELECT_SQL = "select id, login_name as loginName, nickname, truename, "
			+ "gender, mobile, email, avatar_url as avatarUrl, pic_url as picUrl, "
			+ "status, intro, create_time as createTime, update_time as updateTime "
			+ "from sys_user ";
	@Select(SELECT_SQL + " where id = #{id}")
	SysUser getById(Long id);

	@Select(SELECT_SQL + " where login_name = #{loginName}")
	SysUser getByLoginName(String loginName);
	
	/**
	 * 查询列表
	 */
	@Select("select * from sys_user")
	@Results({
		@Result(property = "loginName",  column = "login_name"),
		@Result(property = "avatarUrl",  column = "avatar_url"),
		@Result(property = "picUrl", column = "pic_url"),
		@Result(property = "createTime",  column = "create_time"),
		@Result(property = "updateTime", column = "update_time")
	})
	List<SysUser> query();

	@Select(SELECT_SQL + " where login_name = #{loginName} and login_pass = #{loginPass}")
	SysUser login(@Param("loginName")String loginName, @Param("loginPass")String loginPass);

	@Update("update sys_user set login_pass = #{loginPass} where id = #{id}")
	void updateLoginPassById(@Param("id")Long id, @Param("loginPass")String loginPass);

	@Select("select login_pass from sys_user where id = #{id}")
	String getLoginPassById(Long id);

}
