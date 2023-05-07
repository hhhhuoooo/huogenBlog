package com.huo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huo.domain.ResponseResult;
import com.huo.domain.dto.TagListDto;
import com.huo.domain.entity.Tag;
import com.huo.domain.vo.TagVo;


/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2023-04-30 16:10:47
 */
public interface TagService extends IService<Tag> {

    ResponseResult pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    ResponseResult add(TagListDto tagListDto);

    ResponseResult get(Long id);

    ResponseResult updateTag(TagVo tagVo);

    ResponseResult deleteTag(Long id);

    ResponseResult listAllTag();
}

