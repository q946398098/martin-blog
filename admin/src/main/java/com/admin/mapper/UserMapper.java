package com.admin.mapper;


import com.admin.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

     int addUser(UserEntity userEntity);

     List<UserEntity> getUsersByPage();

     int deleteByPk(long recid);

     int updateUser(UserEntity userEntity);
}
