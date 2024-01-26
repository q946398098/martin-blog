package com.admin.mapper;


import com.admin.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {

     int addUser(UserEntity userEntity);

     List<UserEntity> getUsersByPage(Map<String,String> where);

     int deleteByPk(long recid);

     int batchDelete(List<Long> recids);

     int updateUser(UserEntity userEntity);
}
