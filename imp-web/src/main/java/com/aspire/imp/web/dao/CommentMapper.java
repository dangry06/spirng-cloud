package com.aspire.imp.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.aspire.imp.web.entity.Comment;

@Mapper
public interface CommentMapper {
	String SELECT_SQL = "select id, post_id as postId, author_id as authorId, author_nickname as authorNickname, "
			+ "create_time as createTime, content, comment_parent as commentParent, comment_root as commentRoot "
			+ "from comment ";
	@Select(SELECT_SQL + " where approved_flag = 10 and post_id = #{postId} and comment_parent = 0 order by create_time desc")
	List<Comment> queryParentCommentByPostId(long postId);
	
	@Select(SELECT_SQL + " where approved_flag = 10 and post_id = #{postId} and comment_root = #{commentRootId} order by comment_parent asc")
	List<Comment> querySubCommentByPostId(@Param("postId")long postId, @Param("commentRootId")long commentRootId);

	String INSERT_COLUMN = "post_id, author_id, author_nickname, create_time, content, approved_flag, comment_parent, comment_root";
	String INSERT_VALUE = "#{postId}, #{authorId}, #{authorNickname}, #{createTime}, #{content}, #{approvedFlag}, #{commentParent}, #{commentRoot}";
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	@Insert("insert into comment (" + INSERT_COLUMN + ") values (" + INSERT_VALUE + ")")
	int add(Comment comment);

}
