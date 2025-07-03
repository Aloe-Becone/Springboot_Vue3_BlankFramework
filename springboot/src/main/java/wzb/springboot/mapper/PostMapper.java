package wzb.springboot.mapper;

import org.apache.ibatis.annotations.*;
import wzb.springboot.entity.Post;
import java.util.List;

@Mapper
public interface PostMapper {
    // 根据id查询帖子
    @Select("SELECT * FROM post WHERE id = #{id}")
    Post selectById(Integer id);

    // 根据用户ID查询帖子
    @Select("SELECT * FROM post WHERE user_id = #{userId}")
    List<Post> selectByUserId(Integer userId);

    // 获取所有帖子
    @Select("SELECT * FROM post")
    List<Post> selectAllPosts();

    // 添加帖子
    @Insert("INSERT INTO post (user_id, title, content, images, like) " +
            "VALUES (#{userId}, #{title}, #{content}, #{images}, #{like})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void addPost(Post post);

    // 删除帖子
    @Delete("DELETE FROM post WHERE id = #{id}")
    void deleteById(Integer id);

    // 更新帖子
    @Update("UPDATE post SET " +
            "user_id = #{userId}, " +
            "title = #{title}, " +
            "content = #{content}, " +
            "images = #{images}, " +
            "like = #{like} " +
            "WHERE id = #{id}")
    int updatePost(Post post);

    // 更新点赞数
    @Update("UPDATE post SET like = #{like} WHERE id = #{id}")
    int updateLikeCount(@Param("id") Integer id, @Param("like") Integer like);
}