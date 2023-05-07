package com.huo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @version 1.0
 * @Author huohuozi
 * @Date 2023/5/4 16:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleListDto {
    private String title;
    private String summary;
}
