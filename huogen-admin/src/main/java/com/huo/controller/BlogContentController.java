package com.huo.controller;

import com.huo.domain.ResponseResult;
import com.huo.domain.dto.AddArticleDto;
import com.huo.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version 1.0
 * @Author huohuozi
 * @Date 2023/5/4 15:21
 */
@RestController
@RequestMapping("/content/article")
public class BlogContentController {

//    @Autowired
//    private ArticleService articleService;
//
//
//    @PostMapping
//    public ResponseResult add(@RequestBody AddArticleDto article){
//        return articleService.add(article);
//    }

}
