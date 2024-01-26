package com.admin.controller;


import com.admin.common.Response;
import com.admin.entity.UserEntity;
import com.admin.mapper.UserMapper;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.servlet.http.HttpServletRequest;
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
    public String upsert(@RequestBody UserEntity userentity){
        System.out.println(userentity);
        if (userentity.getCd_user() < 0){
            userMapper.addUser(userentity);
        }else{
            userMapper.updateUser(userentity);
        }
        return "ok";
    };

    @DeleteMapping("/batchDel")
    public Response batchDel(@RequestBody Map<String, Object> json){

        Object s = json.get("recids");
        System.out.println(s instanceof String);  // true
        System.out.println(s instanceof Integer); // true
        System.out.println(s instanceof Boolean); // true
        System.out.println(s instanceof Integer); // false

        System.out.println(s instanceof String);  // false
        System.out.println(s instanceof List<?>); // false



        return new Response(200,"ok");
    }


    @RequestMapping("/getUsersByPage/{pageNum}/{pageSize}")
    public PageInfo<UserEntity> getUsersByPage(@PathVariable("pageNum")int pageNum ,@PathVariable("pageSize") int pageSize) {
        System.out.println(pageNum);
        System.out.println(pageSize);
        PageHelper.startPage(pageNum, pageSize);
        List<UserEntity> users = userMapper.getUsersByPage();
        return new PageInfo<>(users);
    }


    @DeleteMapping("/delete/{recid}")
    public Response delete (@PathVariable("recid") Long recid){
        System.out.println(recid);
        userMapper.deleteByPk(recid);
        return new Response(200,"ok");
    }


}
