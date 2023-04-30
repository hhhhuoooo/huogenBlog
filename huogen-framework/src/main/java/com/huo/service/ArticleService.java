package com.huo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huo.domain.ResponseResult;
import com.huo.domain.entity.Article;

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
}
