package com.admin.mapper;


import com.admin.entity.ArticleEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ArticleMapper {

     int add(ArticleEntity ArticleEntity);

     List<ArticleEntity> getUsersByPage(Map<String,String> where);

     int deleteByPk(long recid);

     int batchDelete(List<Long> recids);

     int update(ArticleEntity ArticleEntity);
}
