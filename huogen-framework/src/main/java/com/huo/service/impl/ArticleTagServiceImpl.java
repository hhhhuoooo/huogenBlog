package com.huo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huo.domain.ResponseResult;
import com.huo.domain.dto.AddArticleDto;
import com.huo.domain.entity.Article;
import com.huo.domain.entity.ArticleTag;
import com.huo.mapper.ArticleTagMapper;
import com.huo.service.ArticleTagService;
import com.huo.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 文章标签关联表(ArticleTag)表服务实现类
 *
 * @author makejava
 * @since 2023-05-04 15:26:58
 */
@Service("articleTagService")
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

}

