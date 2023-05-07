package com.huo.controller;

import com.huo.domain.ResponseResult;
import com.huo.domain.dto.LinkAddDto;
import com.huo.domain.dto.LinkListDto;
import com.huo.domain.dto.TagListDto;
import com.huo.domain.vo.LinkUpdateVo;
import com.huo.domain.vo.PageVo;
import com.huo.domain.vo.TagVo;
import com.huo.service.LinkService;
import com.huo.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @version 1.0
 * @Author huohuozi
 * @Date 2023/5/6 18:19
 */
@RestController
@RequestMapping("/content/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    //显示所有标签
    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, LinkListDto linkListDto){
        return linkService.linkList(pageNum,pageSize,linkListDto);
    }

    //新增标签
    @PostMapping
    public ResponseResult add(@RequestBody LinkAddDto linkAddDto){
        return linkService.add(linkAddDto);
    }


    //删除标签
    @DeleteMapping("/{id}")
    public ResponseResult deleteLink(@PathVariable Long id){
        return linkService.deleteLink(id);
    }

    //  获取标签信息
    @GetMapping("/{id}")
    public ResponseResult get(@PathVariable Long id){
        return linkService.get(id);
    }

    //修改标签信息
    @PutMapping
    public ResponseResult updateLink(@RequestBody LinkUpdateVo linkUpdateVo){
        return linkService.updateLink(linkUpdateVo);
    }



}
