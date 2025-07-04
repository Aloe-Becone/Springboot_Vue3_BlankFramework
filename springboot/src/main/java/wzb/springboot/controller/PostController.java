package wzb.springboot.controller;

import cn.hutool.json.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;

import wzb.springboot.common.*;
import wzb.springboot.entity.Post;
import wzb.springboot.mapper.PostMapper;

@RestController
@RequestMapping("/post")
public class PostController {
    @Resource
    PostMapper postMapper;

    // 获取所有帖子
    @RequestMapping("/getAll")
    public Result getPosts()
    {
        try {
            List<Post> posts = postMapper.selectAllPosts();

            // 确保每个Post对象的images字段被正确解析
            ObjectMapper objectMapper = new ObjectMapper();
            for (Post post : posts) {
                if (post.getImages() != null && !post.getImages().isEmpty()) {
                    // 将数据库中的JSON字符串转换为List<String>
                    List<String> imagesList = objectMapper.readValue(
                            post.getImages(),
                            new TypeReference<>() {}
                    );
                    post.setImagesList(imagesList);
                }
            }
            return Result.success(posts);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error(ResultCodeEnum.SYSTEM_ERROR);
        }
    }

    // 获取用户所有帖子
    @RequestMapping("/getUserPost/{userId}")
    public Result getPosts(@PathVariable Integer userId)
    {
        try {
            List<Post> posts = postMapper.selectAllPosts();

            // 确保每个Post对象的images字段被正确解析
            ObjectMapper objectMapper = new ObjectMapper();
            for (Post post : posts) {
                if (post.getImages() != null && !post.getImages().isEmpty()) {
                    // 将数据库中的JSON字符串转换为List<String>
                    List<String> imagesList = objectMapper.readValue(
                            post.getImages(),
                            new TypeReference<>() {}
                    );
                    post.setImagesList(imagesList);
                }
            }
            return Result.success(posts);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error(ResultCodeEnum.SYSTEM_ERROR);
        }
    }

    // 获取指定ID帖子
    @RequestMapping("/getPost/{postId}")
    public Result getPost(@PathVariable Integer postId)
    {
        try {
            Post post = postMapper.selectById(postId);

            // 确保每个Post对象的images字段被正确解析
            ObjectMapper objectMapper = new ObjectMapper();
            if (post.getImages() != null && !post.getImages().isEmpty()) {
                // 将数据库中的JSON字符串转换为List<String>
                List<String> imagesList = objectMapper.readValue(
                        post.getImages(),
                        new TypeReference<>() {}
                );
                post.setImagesList(imagesList);
            }
            return Result.success(post);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error(ResultCodeEnum.SYSTEM_ERROR);
        }
    }

    //删除帖子
    @RequestMapping("/delete/{postId}")
    public Result deletePosts(@PathVariable Integer postId)
    {
        try {
            postMapper.deleteById(postId);
            return Result.success();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return Result.error(ResultCodeEnum.SYSTEM_ERROR);
        }

    }

    // 添加帖子
    @RequestMapping("/creat")
    public Result createPost(@RequestBody Post post)
    {
        System.out.println(post.getImagesList());
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String jsonString = objectMapper.writeValueAsString(post.getImagesList());
            String toString = post.getImagesList().toString();

            System.out.println("jsonString: " + jsonString);
            System.out.println("toString: " + toString);

            post.setImages(jsonString);
            postMapper.addPost(post);
            return Result.success();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return Result.error(ResultCodeEnum.SYSTEM_ERROR);
        }

    }
    
    // 更新帖子
    @RequestMapping("/update")
    public Result updatePost(@RequestBody Post post)
    {
        try {
            postMapper.updatePost(post);
            return Result.success();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return Result.error(ResultCodeEnum.SYSTEM_ERROR);
        }

    }

    // 点赞帖子
    @RequestMapping("/like/{postId}/{likeCount}")
    public Result likePost(@PathVariable Integer postId, @PathVariable Integer likeCount)
    {
        try {
            postMapper.updateLikeCount(postId, likeCount);
            return Result.success();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return Result.error(ResultCodeEnum.SYSTEM_ERROR);
        }

    }
}
