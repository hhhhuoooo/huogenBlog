package com.huo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huo.constants.SystemConstants;
import com.huo.domain.ResponseResult;
import com.huo.domain.entity.Article;
import com.huo.domain.entity.Category;
import com.huo.domain.vo.CategoryVo;
import com.huo.mapper.CategoryMapper;
import com.huo.service.ArticleService;
import com.huo.service.CategoryService;
import com.huo.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired CategoryService categoryService;

    @Override
    public ResponseResult getCategoryList() {
        List<Article> articles = articleService.list();

        //可以采用stream表示更方便
        List<Category> categories=new ArrayList<>();
        for (Article article:articles) {
            if (article.getStatus().equals("0")){
                Category category = categoryService.getById(article.getCategoryId());
                if (category.getStatus().equals("0")){
                    categories.add(category);
                }

            }
        }
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }
}

