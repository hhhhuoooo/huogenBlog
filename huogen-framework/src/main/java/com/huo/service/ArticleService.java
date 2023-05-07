package com.huo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huo.domain.ResponseResult;
import com.huo.domain.dto.AddArticleDto;
import com.huo.domain.dto.ArticleListDto;
import com.huo.domain.dto.ContentArticleListDto;
import com.huo.domain.entity.Article;
import com.huo.domain.vo.PageVo;

/**
 * @version 1.0
 * @Author huohuozi
 * @Date 2023/4/24 17:52
 */
public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, long categoryId);

    ResponseResult getArticleDetail(long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult add(AddArticleDto article);

    ResponseResult<PageVo> List(Integer pageNum, Integer pageSize, ArticleListDto articleListDto);

    ResponseResult get(Long id);

    ResponseResult updateArticle(ContentArticleListDto contentArticleListDto);

    ResponseResult deleteArticle(Long id);
}
