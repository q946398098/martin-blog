package com.admin.controller;


import com.admin.common.Response;
import com.admin.entity.UserEntity;
import com.admin.mapper.UserMapper;
import com.admin.tool.Common;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.servlet.http.HttpServletRequest;
import net.sf.jsqlparser.statement.create.table.Index;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/upsert")
    public Response upsert(@RequestBody UserEntity userentity){
        System.out.println(userentity);
        if (userentity.getCd_user() < 0){
            userMapper.addUser(userentity);
        }else{
            userMapper.updateUser(userentity);
        }
        return new Response(200,"success");
    };

    @DeleteMapping("/batchDel")
    public Response batchDel(@RequestBody Map<String, Object> json){

        List<Long> s = (List<Long>) json.get("recids");

        System.out.println(s.size());
        if (s.size() > 0){
            userMapper.batchDelete(s);
        }else{
          return new Response(400,"请勾选要删除的东西");
        }
        return new Response(200,"ok");
    }


    @RequestMapping("/getUsersByPage/{pageNum}/{pageSize}")
    public PageInfo<UserEntity> getUsersByPage(@PathVariable("pageNum")int pageNum ,@PathVariable("pageSize") int pageSize , @RequestBody Map<String, Object> json) throws JsonProcessingException {
        Map<String,String>  where_map = (Map<String, String>) json.get("filter");
        System.out.println(where_map);
        System.out.println(Common.getType(where_map));
//       ObjectMapper objectMapper = new ObjectMapper();
//        UserEntity userentity =  objectMapper.readValue(s, UserEntity.class);
//        System.out.println(userentity.getNr_age());
        PageHelper.startPage(pageNum, pageSize);
        List<UserEntity> users = userMapper.getUsersByPage(where_map);
        return new PageInfo<>(users);
    }


    @DeleteMapping("/delete/{recid}")
    public Response delete (@PathVariable("recid") Long recid){
        System.out.println(recid);
        userMapper.deleteByPk(recid);
        return new Response(200,"ok");
    }


}
