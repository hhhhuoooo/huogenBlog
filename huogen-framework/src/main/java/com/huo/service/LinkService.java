package com.huo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huo.domain.ResponseResult;
import com.huo.domain.dto.LinkAddDto;
import com.huo.domain.dto.LinkListDto;
import com.huo.domain.entity.Link;
import com.huo.domain.vo.LinkUpdateVo;
import com.huo.domain.vo.PageVo;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2023-04-27 15:16:26
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAlllink();

    ResponseResult<PageVo> linkList(Integer pageNum, Integer pageSize, LinkListDto linkListDto);

    ResponseResult add(LinkAddDto linkAddDto);

    ResponseResult deleteLink(Long id);

    ResponseResult get(Long id);

    ResponseResult updateLink(LinkUpdateVo linkUpdateVo);
}

