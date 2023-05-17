package com.huo.job;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.huo.domain.entity.Article;
import com.huo.service.ArticleService;
import com.huo.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @Author huohuozi
 * @Date 2023/4/30 11:55
 */
@Component
public class updateViewCount {
    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleService articleService;



    @Scheduled(cron = "0/55 * * * * ?")
    public void updateViewCount() {
        //获取redis中浏览量数据
        Map<String, Integer> viewCountMap = redisCache.getCacheMap("article:viewCount");

        List<Article> articles = viewCountMap.entrySet()
                .stream()
                .map(entry ->
                        new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());

        for (Article article : articles) {
            LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Article::getId, article.getId());
            updateWrapper.set(Article::getViewCount, article.getViewCount());
            articleService.update(updateWrapper);
        }
    }
}
