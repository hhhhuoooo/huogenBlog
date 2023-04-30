package com.huo.controller;

import com.huo.domain.ResponseResult;
import com.huo.domain.entity.Article;
import com.huo.service.ArticleService;
import com.huo.service.impl.ArticleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @version 1.0
 * @Author huohuozi
 * @Date 2023/4/24 17:56
 */

//实现分类
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

//    @GetMapping("/list")
//    public List<Article> test(){
//        return articleService.list();
//    }

//    获取热门文章
    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList(){
        return articleService.hotArticleList();

    }
//    {pageNum}/{pageSize}/{categoryId}
    @GetMapping("/articleList")
    public ResponseResult ArticleList( Integer pageNum,  Integer pageSize,  long categoryId){

        return articleService.articleList(pageNum,pageSize,categoryId);

    }


    //浏览文章详情
    @GetMapping("/{id}")
    public ResponseResult getArticleDetail(@PathVariable("id") long id){

        return articleService.getArticleDetail(id);

    }

    @PutMapping("/updateViewCount/{id}")
    public ResponseResult updateViewCount(@PathVariable("id") Long id){
        return articleService.updateViewCount(id);
    }


}
