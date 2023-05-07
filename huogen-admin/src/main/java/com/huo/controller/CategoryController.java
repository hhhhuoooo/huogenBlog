package com.huo.controller;

import com.huo.domain.ResponseResult;
import com.huo.domain.dto.CategoryAddDto;
import com.huo.domain.dto.CategoryListDto;
import com.huo.domain.dto.CategoryUpdateDto;
import com.huo.domain.dto.TagListDto;
import com.huo.domain.vo.PageVo;
import com.huo.domain.vo.TagVo;
import com.huo.service.CategoryService;
import com.huo.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @version 1.0
 * @Author huohuozi
 * @Date 2023/5/4 15:03
 */
@RestController
@RequestMapping("/content/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory(){
        return categoryService.listAllCategory();
    }



    //显示所有标签
    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, CategoryListDto categoryListDto){
        return categoryService.categoryList(pageNum,pageSize,categoryListDto);
    }

    //新增标签
    @PostMapping
    public ResponseResult add(@RequestBody CategoryAddDto categoryAddDto){
        return categoryService.add(categoryAddDto);
    }


    //删除标签
    @DeleteMapping("/{id}")
    public ResponseResult deleteCategory(@PathVariable Long id){
        return categoryService.deleteCategory(id);
    }

    //  获取标签信息
    @GetMapping("/{id}")
    public ResponseResult get(@PathVariable Long id){
        return categoryService.get(id);
    }

    //修改标签信息
    @PutMapping
    public ResponseResult updateCategory(@RequestBody CategoryUpdateDto categoryUpdateDto){
        return categoryService.updateCategory(categoryUpdateDto);
    }
//
//
//    //在写博文中  显示所有标签
//    @GetMapping("/listAllTag")
//    public ResponseResult listAllTag(){
//        return tagService.listAllTag();
//    }

}
