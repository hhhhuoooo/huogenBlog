package com.huo.controller;

import com.huo.constants.SystemConstants;
import com.huo.domain.ResponseResult;
import com.huo.domain.entity.Comment;
import com.huo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @version 1.0
 * @Author huohuozi
 * @Date 2023/4/27 18:13
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    //查看文章评论列表
    @GetMapping("/commentList")
    public ResponseResult commentList(Long articleId,Integer pageNum,Integer pageSize){
        return commentService
                .commentList(SystemConstants.ARTICLE_COMMENT,articleId,pageNum,pageSize);
    }


    //新增文章评论
    @PostMapping
    public ResponseResult addComment(@RequestBody Comment comment){
        return commentService.addComment(comment);
    }
    //新增友链评论
    @GetMapping("/linkCommentList")
    public ResponseResult linkCommentList(Integer pageNum,Integer pageSize){
        return commentService.commentList(SystemConstants.LINK_COMMENT,null,pageNum,pageSize);
    }


}
