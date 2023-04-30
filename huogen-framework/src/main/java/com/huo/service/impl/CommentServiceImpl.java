package com.huo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huo.constants.SystemConstants;
import com.huo.domain.ResponseResult;
import com.huo.domain.entity.Comment;
import com.huo.domain.vo.CommentVo;
import com.huo.domain.vo.PageVo;
import com.huo.enums.AppHttpCodeEnum;
import com.huo.handler.exception.SystemException;
import com.huo.mapper.CommentMapper;
import com.huo.service.CommentService;
import com.huo.service.UserService;
import com.huo.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2023-04-27 18:10:25
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {


    @Autowired
    private UserService userService;
    @Override
    public ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
        //查询文章的根评论
        LambdaQueryWrapper<Comment> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemConstants.ARTICLE_COMMENT.equals(commentType),Comment::getArticleId,articleId);
        queryWrapper.eq(Comment::getRootId,-1);
        queryWrapper.eq(Comment::getType,commentType);

        Page<Comment> page=new Page(pageNum,pageSize);
        page(page,queryWrapper);

        List<Comment> comments = page.getRecords();
        List<CommentVo> commentVoList = toCommentVoList(comments);

        for (CommentVo commentVo:commentVoList) {
            List<CommentVo> children = getChildren(commentVo.getId());
            commentVo.setChildren(children);
        }

        return ResponseResult.okResult(new PageVo(commentVoList,page.getTotal()));
    }



    private List<CommentVo> getChildren(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId,id);
        queryWrapper.orderByAsc(Comment::getCreateTime);

        List<Comment> comments=list(queryWrapper);
        List<CommentVo> commentVos = toCommentVoList(comments);
        return  commentVos;
    }


    public List<CommentVo> toCommentVoList(List<Comment> list){
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(list, CommentVo.class);

        for (CommentVo commentVo :commentVos) {
            String nickName=userService.getById(commentVo.getCreateBy()).getNickName();
            commentVo.setUsername(nickName);

            if (commentVo.getToCommentId()!=-1){
                String toCommentUserName=userService
                        .getById(commentVo.getToCommentUserId()).getNickName();
                commentVo.setToCommentUserName(toCommentUserName);
            }
        }

        return  commentVos;
    }




    @Override
    public ResponseResult addComment(Comment comment) {
        if (!StringUtils.hasText(comment.getContent())){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        save(comment);
        return null;
    }
}

