package com.aspire.imp.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.aspire.imp.web.entity.PostTerm;
import com.aspire.imp.web.entity.Term;

@Mapper
public interface PostTermMapper {
	@Select("select t.id, t.name from term t, post_term pt where t.id = pt.term_id and pt.post_id = #{postId}")
	List<Term> queryTermByPostId(Long postId);

	@Insert("insert into post_term (post_id, term_id) values (#{postId}, #{termId})")
	int add(PostTerm pt);

	@Delete("delete from post_term where post_id = #{postId}")
	void deleteByPostId(Long postId);
}
