package com.huo.controller;

import com.huo.domain.ResponseResult;
import com.huo.domain.dto.ArticleListDto;
import com.huo.domain.dto.ContentArticleListDto;
import com.huo.domain.dto.TagListDto;
import com.huo.domain.entity.Article;
import com.huo.domain.vo.PageVo;
import com.huo.service.ArticleService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @version 1.0
 * @Author huohuozi
 * @Date 2023/4/24 17:56
 */

//实现分类
@RestController
@RequestMapping("content/article")
public class ContentArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/list")
    public ResponseResult<PageVo> List(Integer pageNum, Integer pageSize, ArticleListDto articleListDto){

        return articleService.List(pageNum,pageSize,articleListDto);

    }

    @GetMapping("/{id}")
    public ResponseResult get(@PathVariable Long id){
        return articleService.get(id);

    }

    @PutMapping
    public ResponseResult updateArticle(@RequestBody ContentArticleListDto contentArticleListDto){
        return articleService.updateArticle(contentArticleListDto);

    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteArticle(@PathVariable Long id){
        return articleService.deleteArticle(id);

    }







}
