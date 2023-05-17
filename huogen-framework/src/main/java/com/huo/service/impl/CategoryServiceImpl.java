package com.huo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huo.constants.SystemConstants;
import com.huo.domain.ResponseResult;
import com.huo.domain.dto.CategoryAddDto;
import com.huo.domain.dto.CategoryListDto;
import com.huo.domain.dto.CategoryUpdateDto;
import com.huo.domain.entity.Article;
import com.huo.domain.entity.Category;
import com.huo.domain.entity.Tag;
import com.huo.domain.vo.CategoryUpdateVo;
import com.huo.domain.vo.CategoryVo;
import com.huo.domain.vo.PageVo;
import com.huo.mapper.CategoryMapper;
import com.huo.service.ArticleService;
import com.huo.service.CategoryService;
import com.huo.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2023-04-27 09:50:20
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ResponseResult getCategoryList() {
//        List<Article> articles = articleService.list();
//        //可以采用stream表示更方便
//        List<Category> categories=new ArrayList<>();
//        for (Article article:articles) {
//            if (article.getStatus().equals("0")){
//                Category category = categoryService.getById(article.getCategoryId());
//                if (category.getStatus().equals("0")){
//                    categories.add(category);
//                }
//            }
//        }
        //查询文章表  状态为已发布的文章
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(queryWrapper);
        //获取文章的分类id，并且去重
        Set<Long> categoryIds = articleList.stream()
                .map(article -> article.getCategoryId())
                .collect(Collectors.toSet());

        //查询分类表
        List<Category> categories = listByIds(categoryIds);
        categories = categories.stream().
                filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());

        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public ResponseResult listAllCategory() {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getStatus, SystemConstants.NORMAL);

        List<Category> categories = list(queryWrapper);
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);

        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public ResponseResult<PageVo> categoryList(Integer pageNum, Integer pageSize, CategoryListDto categoryListDto) {

        LambdaQueryWrapper<Category> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(categoryListDto.getName()),Category::getName,categoryListDto.getName());
        queryWrapper.like(StringUtils.hasText(categoryListDto.getStatus()),Category::getStatus,categoryListDto.getStatus());

        Page page=new Page(pageNum,pageSize);
        page(page,queryWrapper);

        PageVo pageVo=new PageVo(page.getRecords(),page.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult add(CategoryAddDto categoryAddDto) {
        Category category = BeanCopyUtils.copyBean(categoryAddDto,Category.class);
        categoryMapper.insert(category);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteCategory(Long id) {
        categoryMapper.deleteById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult get(Long id) {
        Category category = categoryMapper.selectById(id);
        CategoryUpdateVo categoryUpdateVo = BeanCopyUtils.copyBean(category, CategoryUpdateVo.class);
        return ResponseResult.okResult(categoryUpdateVo);
    }

    @Override
    public ResponseResult updateCategory(CategoryUpdateDto categoryUpdateDto) {
        Category category = BeanCopyUtils.copyBean(categoryUpdateDto,Category.class);
        categoryMapper.updateById(category);
        return ResponseResult.okResult();
    }

}

