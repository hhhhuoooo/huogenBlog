package com.huo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huo.constants.SystemConstants;
import com.huo.domain.ResponseResult;
import com.huo.domain.dto.LinkAddDto;
import com.huo.domain.dto.LinkListDto;
import com.huo.domain.entity.Category;
import com.huo.domain.entity.Link;
import com.huo.domain.entity.Tag;
import com.huo.domain.vo.CategoryUpdateVo;
import com.huo.domain.vo.LinkUpdateVo;
import com.huo.domain.vo.LinkVo;
import com.huo.domain.vo.PageVo;
import com.huo.mapper.LinkMapper;
import com.huo.service.LinkService;
import com.huo.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2023-04-27 15:16:26
 */
@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {


    @Autowired
    private LinkMapper linkMapper;

    @Override
    public ResponseResult getAlllink() {
        LambdaQueryWrapper<Link> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);

        List<Link> links=list(queryWrapper);
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(links, LinkVo.class);

        return ResponseResult.okResult(linkVos);
    }

    @Override
    public ResponseResult<PageVo> linkList(Integer pageNum, Integer pageSize, LinkListDto linkListDto) {
        LambdaQueryWrapper<Link> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(linkListDto.getName()),Link::getName,linkListDto.getName());
        queryWrapper.like(StringUtils.hasText(linkListDto.getStatus()),Link::getStatus,linkListDto.getStatus());
        Page page=new Page(pageNum,pageSize);
        page(page,queryWrapper);

        PageVo pageVo=new PageVo(page.getRecords(),page.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult add(LinkAddDto linkAddDto) {
        Link link = BeanCopyUtils.copyBean(linkAddDto,Link.class);
        linkMapper.insert(link);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteLink(Long id) {
        linkMapper.deleteById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult get(Long id) {
        Link link = linkMapper.selectById(id);
        LinkUpdateVo linkUpdateVo = BeanCopyUtils.copyBean(link, LinkUpdateVo.class);
        return ResponseResult.okResult(linkUpdateVo);
    }

    @Override
    public ResponseResult updateLink(LinkUpdateVo linkUpdateVo) {
        Link link = BeanCopyUtils.copyBean(linkUpdateVo,Link.class);
        linkMapper.updateById(link);
        return ResponseResult.okResult();
    }
}

