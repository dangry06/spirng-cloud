package com.aspire.imp.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.aspire.imp.web.entity.Post;

@Mapper
public interface PostMapper {
	String SELECT_SQL = "select p.id, p.author_id as authorId, p.content, p.title, p.excerpt, "
			+ "p.status, p.comment_status as commentStatus, p.header_image as headerImage, "
			+ "p.comment_count as commentCount, p.thumbsup_count as thumbsupCount, "
			+ "p.create_time as createTime, p.update_time as updateTime ";
	@Select(SELECT_SQL + " from post p where p.status = 10 order by p.create_time desc")
	List<Post> query();
	
	@Select(SELECT_SQL + " from post p, post_term pt where p.id = pt.post_id and pt.term_id = #{termId} and p.status = 10 order by p.create_time desc")
	List<Post> queryByTerm(Integer termId);
	
	@Select(SELECT_SQL + " from post p where p.author_id = #{authorId} and p.status = #{status} order by p.create_time desc")
	List<Post> queryByAuthor(@Param("authorId")Long authorId, @Param("status")Integer status);
	
	@Select(SELECT_SQL + " from post p where p.id = #{id} ")
	Post get(Long id);

	String INSERT_COLUMN = "author_id, content, title, excerpt, status, comment_status, header_image, create_time, update_time, post_parent, post_name, post_mime_type";
	String INSERT_VALUE = "#{authorId}, #{content}, #{title}, #{excerpt}, #{status}, #{commentStatus}, #{headerImage}, #{createTime}, #{updateTime}, #{postParent}, #{postName}, #{postMimeType}";
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	@Insert("insert into post (" + INSERT_COLUMN + ") values (" + INSERT_VALUE + ")")
	int add(Post post);

	@Update("update post set post_parent = #{postParent}  where post_parent = #{oldPostParent}")
	int updateSubPost(@Param("postParent")Long postParent, @Param("oldPostParent")Long oldPostParent);

	String UPDATE_COLUMN = "author_id = #{authorId}, content = #{content}, title = #{title}, excerpt = #{excerpt}, "
			+ "status = #{status}, comment_status = #{commentStatus}, header_image = #{headerImage},  update_time = #{updateTime}";
	@Update("update post set " + UPDATE_COLUMN + " where id = #{id}")
	int update(Post post);

	@Update("update post set status = #{status} where id = #{id}")
	void updatePostStatus(@Param("id")Long id, @Param("status")Integer status);

	@Update("update post set thumbsup_count = thumbsup_count+1 where id = #{id}")
	void updatePostThumbsupCount(Long id);

	@Select("select thumbsup_count from post p where p.id = #{id} ")
	Long getThumbsupCountById(Long id);

	@Update("update post set comment_count = comment_count+1 where id = #{id}")
	void updateCommentCountById(Long id);
	
}
