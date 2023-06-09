package com.huo.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @version 1.0
 * @Author huohuozi
 * @Date 2023/4/28 18:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

public class CommentVo {

    private Long id;
    //文章id
    private Long articleId;
    //根评论id
    private Long rootId;
    //评论内容
    private String content;
    //所回复的目标评论的userid
    private Long toCommentUserId;
    //回复目标评论id
    private Long toCommentId;

    private Long createBy;

    private Date createTime;

    private String toCommentUserName;

    private  String username;

    private List<CommentVo> children;



}
