package com.huo.controller;

import com.huo.domain.ResponseResult;
import com.huo.domain.dto.TagListDto;
import com.huo.domain.vo.PageVo;
import com.huo.domain.vo.TagVo;
import com.huo.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    //显示所有标签
    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, TagListDto tagListDto){
        return tagService.pageTagList(pageNum,pageSize,tagListDto);
    }

    //新增标签
    @PostMapping
    public ResponseResult add(@RequestBody TagListDto tagListDto){
        return tagService.add(tagListDto);
    }


    //删除标签
    @DeleteMapping("/{id}")
    public ResponseResult deleteTag(@PathVariable Long id){
        return tagService.deleteTag(id);
    }

//  获取标签信息
    @GetMapping("/{id}")
    public ResponseResult get(@PathVariable Long id){
        return tagService.get(id);
    }

    //修改标签信息
    @PutMapping
    public ResponseResult updateTag(@RequestBody TagVo tagVo){
        return tagService.updateTag(tagVo);
    }


    //在写博文中  显示所有标签
    @GetMapping("/listAllTag")
    public ResponseResult listAllTag(){
        return tagService.listAllTag();
    }






}

