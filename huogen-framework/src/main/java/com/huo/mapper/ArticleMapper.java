package com.huo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huo.domain.entity.Article;
import com.huo.domain.entity.ArticleTag;
import com.huo.domain.entity.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @version 1.0
 * @Author huohuozi
 * @Date 2023/4/24 17:51
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
    List<ArticleTag> getArticleTaglist(Long id);
}
