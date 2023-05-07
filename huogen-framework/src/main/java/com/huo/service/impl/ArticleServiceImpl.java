package com.huo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huo.constants.SystemConstants;
import com.huo.domain.ResponseResult;
import com.huo.domain.dto.AddArticleDto;
import com.huo.domain.dto.ArticleListDto;
import com.huo.domain.dto.ContentArticleListDto;
import com.huo.domain.entity.Article;
import com.huo.domain.entity.ArticleTag;
import com.huo.domain.entity.Category;
import com.huo.domain.vo.*;
import com.huo.mapper.ArticleMapper;
import com.huo.service.ArticleService;

import com.huo.service.ArticleTagService;
import com.huo.service.CategoryService;
import com.huo.utils.BeanCopyUtils;
import com.huo.utils.RedisCache;
import com.huo.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @Author huohuozi
 * @Date 2023/4/24 17:53
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisCache redisCache;


    @Autowired
    private ArticleTagService articleTagService;

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public ResponseResult hotArticleList() {
        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
        //必须是正式文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //按照浏览量进行排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //最多只查询10条
        Page<Article> page=new Page(1,10);
        page(page,queryWrapper);

        List<Article> articles=page.getRecords();

        //Bean拷贝 两个对象的属性名要一致！
//        List<HotArticleVo> articleVos=new ArrayList<>();
//        for (Article article:articles) {
//            HotArticleVo vo=new HotArticleVo();
//            BeanUtils.copyProperties(article,vo);
//            articleVos.add(vo);
//
//        }
        List<HotArticleVo> articleVos= BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);
        return ResponseResult.okResult(articleVos);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, long categoryId) {

        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();

        queryWrapper.eq(Objects.nonNull(categoryId)&&categoryId>0,Article::getCategoryId,categoryId);
        queryWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        queryWrapper.orderByDesc(Article::getIsTop);

        Page<Article> page=new Page(pageNum,pageSize);
        page(page,queryWrapper);
        List<Article> articles = page.getRecords();

        for (Article article:articles) {
            Category category = categoryService.getById(article.getCategoryId());
            article.setCategoryName(category.getName());

        }
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);

        PageVo pageVo = new PageVo(articleListVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(long id) {
        //根据id查询文章
        Article article = getById(id);
        //从redis中获取viewCount
        Integer viewCount = redisCache.getCacheMapValue("article:viewCount", String.valueOf(id));
        article.setViewCount(viewCount.longValue());
        //转换成VO
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        //根据分类id查询分类名
        Long categoryId = articleDetailVo.getCategoryId();
        Category category = categoryService.getById(categoryId);
        if(category!=null){
            articleDetailVo.setCategoryName(category.getName());
        }
        //封装响应返回
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        //更新redis中对应 id的浏览量
        redisCache.incrementCacheMapValue("article:viewCount",id.toString(),1);
        return ResponseResult.okResult();
    }



    //在后端中写博客


    @Override
    @Transactional
    public ResponseResult add(AddArticleDto articleDto) {
        //添加 博客
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        save(article);


        List<ArticleTag> articleTags = articleDto.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());

        //添加 博客和标签的关联
        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();
    }


    //在后端中获取文章列表
    @Override
    public ResponseResult<PageVo> List(Integer pageNum, Integer pageSize, ArticleListDto articleListDto) {
        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getDelFlag,SystemConstants.ARTICLE_DELETE_NORMAL);
        //实现模糊查询标题和摘要
        queryWrapper.like(StringUtils.hasText(articleListDto.getTitle()), Article::getTitle,articleListDto.getTitle());
        queryWrapper.like(StringUtils.hasText(articleListDto.getSummary()),Article::getTitle,articleListDto.getSummary());
        Page page=new Page(pageNum,pageSize);
        page(page,queryWrapper);
        List<ContentArticleListVo> ContentArticleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ContentArticleListVo.class);


        return ResponseResult.okResult(new PageVo(ContentArticleListVos,page.getTotal()));
    }

    @Override
    public ResponseResult get(Long id) {
        if (StringUtils.hasText(id.toString())){
            Article article = articleMapper.selectById(id);

            //在数据库中通过连表查询出文章的tag标签
            List<ArticleTag> taglist = articleMapper.getArticleTaglist(id);
            List<String> tags=new ArrayList<>();
//            将查询到的数据转换成String类型的集合
            for (ArticleTag articleTag:taglist) {
                String tag = articleTag.getTagId().toString();
                tags.add(tag);
            }

            //封装到Vo中
            ContentArticleListVo contentArticleListVo = BeanCopyUtils.copyBean(article, ContentArticleListVo.class);
            contentArticleListVo.setTags(tags);
            return ResponseResult.okResult(contentArticleListVo);
        }
        return null;
    }

    @Override
    public ResponseResult updateArticle(ContentArticleListDto dto) {

        Article article = BeanCopyUtils.copyBean(dto, Article.class);
        updateById(article);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteArticle(Long id) {
        if (StringUtils.hasText(id.toString())){
            articleMapper.deleteById(id);
        }

        return ResponseResult.okResult();
    }


}

