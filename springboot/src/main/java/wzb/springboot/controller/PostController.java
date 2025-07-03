package wzb.springboot.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import wzb.springboot.common.*;
import wzb.springboot.entity.Post;
import wzb.springboot.mapper.PostMapper;

@RestController
@RequestMapping("/post")
public class PostController {
    @Resource
    PostMapper postMapper;

    // 获取所有帖子
    @GetMapping("/get")
    public Result getPosts()
    {
        List<Post> posts = postMapper.selectAllPosts();
        return Result.success(posts);
    }

    // 获取所有用户帖子
    @GetMapping("/get/{userId}")
    public Result getPosts(@PathVariable Integer userId)
    {
        List<Post> posts = postMapper.selectByUserId(userId);
        return Result.success(posts);
    }

    // 获取指定ID帖子
    @GetMapping("/get/{postId}")
    public Result getPost(@PathVariable Integer postId)
    {
        Post post = postMapper.selectById(postId);
        return Result.success(post);
    }

    //删除帖子
    @GetMapping("/delete/{postId}")
    public Result deletePosts(@PathVariable Integer postId)
    {
        try {
            postMapper.deleteById(postId);
            return Result.success();
        }
        catch (Exception e){
            return Result.error(ResultCodeEnum.SYSTEM_ERROR);
        }

    }

    // 添加帖子
    @PostMapping("/creat")
    public Result createPost(@RequestBody Post post)
    {
        try {
            postMapper.addPost(post);
            return Result.success();
        }
        catch (Exception e){
            return Result.error(ResultCodeEnum.SYSTEM_ERROR);
        }

    }
}
