package com.admin.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ArticleEntity {
    private int cd_article = -1;

    private String ds_article;

    private String ds_content;

    private Date dt_deactivated;
}



