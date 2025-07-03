package wzb.springboot.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import wzb.springboot.common.*;
import wzb.springboot.entity.Comment;
import wzb.springboot.mapper.CommentMapper;

// CommentController.java
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Resource
    CommentMapper commentMapper;

    // 获取帖子所有评论
    @RequestMapping ("/get/{postId}")
    public Result getComments(@PathVariable Integer postId)
    {
        List<Comment> comments = commentMapper.selectByPostId(postId);
        return Result.success(comments);
    }

    //删除评论
    @RequestMapping("/delete/{commentId}")
    public Result deleteComments(@PathVariable Integer commentId)
    {
        try {
            commentMapper.deleteById(commentId);
            return Result.success();
        }
        catch (Exception e){
            return Result.error(ResultCodeEnum.SYSTEM_ERROR);
        }

    }

    // 添加评论
    @RequestMapping("/creat")
    public Result createComment(@RequestBody Comment comment)
    {
        try {
            commentMapper.addComment(comment);
            return Result.success();
        }
        catch (Exception e){
            return Result.error(ResultCodeEnum.SYSTEM_ERROR);
        }

    }
}