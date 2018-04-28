package com.aspire.imp.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.aspire.imp.web.entity.Term;

@Mapper
public interface TermMapper {

	@Select("select id, name, pic1, pic2, pic3, remark, sort from term where id != 1 order by sort asc")
	List<Term> query();
}
