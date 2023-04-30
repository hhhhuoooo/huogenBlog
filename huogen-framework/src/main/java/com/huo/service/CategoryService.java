package com.huo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huo.domain.ResponseResult;
import com.huo.domain.entity.Category;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2023-04-27 09:50:20
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();
}

