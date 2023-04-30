package com.huo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huo.domain.ResponseResult;
import com.huo.domain.entity.Link;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2023-04-27 15:16:26
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAlllink();
}

