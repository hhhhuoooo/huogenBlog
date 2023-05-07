package com.huo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huo.domain.ResponseResult;
import com.huo.domain.dto.TagListDto;
import com.huo.domain.entity.Tag;
import com.huo.domain.vo.PageVo;
import com.huo.domain.vo.TagVo;
import com.huo.mapper.TagMapper;
import com.huo.service.TagService;
import com.huo.utils.BeanCopyUtils;
import com.huo.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2023-04-30 16:10:47
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {


    @Autowired
    private TagMapper tagMapper;

    @Override
    public ResponseResult pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {


        LambdaQueryWrapper<Tag> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(tagListDto.getName()),Tag::getName,tagListDto.getName());
        queryWrapper.like(StringUtils.hasText(tagListDto.getRemark()),Tag::getRemark,tagListDto.getRemark());
        Page page=new Page(pageNum,pageSize);
        page(page,queryWrapper);

        PageVo pageVo=new PageVo(page.getRecords(),page.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult add(TagListDto tagListDto) {
//        1、获取用户id
        long userId = SecurityUtils.getLoginUser().getUser().getId();
//        2、获取当前时间
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());

//        3、将所得值传入数据库
        Tag tag=new Tag();
        tag.setName(tagListDto.getName());
        tag.setRemark(tagListDto.getRemark());
        tag.setCreateBy(userId);
        tag.setCreateTime(date);

        tagMapper.insert(tag);
        return ResponseResult.okResult();
    }


    @Override
    public ResponseResult deleteTag(Long id) {
        if (StringUtils.hasText(id.toString())){
            tagMapper.deleteById(id);
        }

        return ResponseResult.okResult();
    }



    @Override
    public ResponseResult get(Long id) {

//        1、第一种方法
//        LambdaQueryWrapper<Tag> queryWrapper=new LambdaQueryWrapper<>();
//        if (StringUtils.hasText(id.toString())){
//            queryWrapper.eq(Tag::getId,id);
//            List<Tag> tagList = list(queryWrapper);
//            TagVo tagVo = BeanCopyUtils.copyBean(tagList.get(0), TagVo.class);
//            return ResponseResult.okResult(tagVo);
//        }
//        2、第二种方法
        if (StringUtils.hasText(id.toString())){
            Tag tag = tagMapper.selectById(id);
            TagVo tagVo = BeanCopyUtils.copyBean(tag, TagVo.class);
            return ResponseResult.okResult(tagVo);
        }
        return null;
    }

    @Override
    public ResponseResult updateTag(TagVo tagVo) {
        //1、获取用户id
        Long id = SecurityUtils.getLoginUser().getUser().getId();

//        2、获取当前时间
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());

//        3、将所得值传入数据库
        Tag tag=new Tag();
        tag.setName(tagVo.getName());
        tag.setRemark(tagVo.getRemark());
        tag.setUpdateBy(id);
        tag.setUpdateTime(date);
        tag.setId(tagVo.getId());

        tagMapper.updateById(tag);

        return ResponseResult.okResult();
    }




    @Override
    public ResponseResult listAllTag() {
        List<Tag> tags = list();
        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(tags, TagVo.class);
        return ResponseResult.okResult(tagVos);
    }


}

