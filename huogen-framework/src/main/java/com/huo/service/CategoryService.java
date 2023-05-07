package com.huo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huo.domain.ResponseResult;
import com.huo.domain.dto.CategoryAddDto;
import com.huo.domain.dto.CategoryListDto;
import com.huo.domain.dto.CategoryUpdateDto;
import com.huo.domain.entity.Category;
import com.huo.domain.vo.PageVo;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2023-04-27 09:50:20
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();

    ResponseResult listAllCategory();

    ResponseResult<PageVo> categoryList(Integer pageNum, Integer pageSize, CategoryListDto categoryListDto);

    ResponseResult add(CategoryAddDto categoryAddDto);

    ResponseResult deleteCategory(Long id);

    ResponseResult get(Long id);

    ResponseResult updateCategory(CategoryUpdateDto categoryUpdateDto);
}

