package com.huo.service;

import com.huo.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @version 1.0
 * @Author huohuozi
 * @Date 2023/4/29 11:36
 */
public interface UploadService {
    ResponseResult uploadImg(MultipartFile img);
}
