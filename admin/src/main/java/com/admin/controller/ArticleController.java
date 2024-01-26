package com.admin.controller;
import com.admin.common.Response;
import com.admin.entity.ArticleEntity;
import com.admin.mapper.ArticleMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleMapper articlemapper;

    @RequestMapping("/upsert")
    public Response upsert(@RequestBody ArticleEntity articleentity){
        System.out.println(articleentity);
        if (articleentity.getCd_article() < 0){
            articlemapper.add(articleentity);
        }else{
            articlemapper.update(articleentity);
        }
        return new Response(200,"success");
    };

    @DeleteMapping("/batchDel")
    public Response batchDel(@RequestBody Map<String, Object> json){

        List<Long> s = (List<Long>) json.get("recids");

        System.out.println(s.size());
        if (s.size() > 0){
            articlemapper.batchDelete(s);
        }else{
          return new Response(400,"请勾选要删除的东西");
        }
        return new Response(200,"ok");
    }


    @RequestMapping("/getListByPage/{pageNum}/{pageSize}")
    public PageInfo<ArticleEntity> getUsersByPage(@PathVariable("pageNum")int pageNum ,@PathVariable("pageSize") int pageSize , @RequestBody Map<String, Object> json) throws JsonProcessingException {
        Map<String,String>  where_map = (Map<String, String>) json.get("filter");

        PageHelper.startPage(pageNum, pageSize);
        List<ArticleEntity> users = articlemapper.getUsersByPage(where_map);
        return new PageInfo<>(users);
    }


    @DeleteMapping("/delete/{recid}")
    public Response delete (@PathVariable("recid") Long recid){
        System.out.println(recid);
        articlemapper.deleteByPk(recid);
        return new Response(200,"ok");
    }


}
