package wzb.springboot.mapper;

import org.apache.ibatis.annotations.*;
import wzb.springboot.entity.Comment;
import java.util.List;

@Mapper
public interface CommentMapper {
    // 根据id查询评论
    @Select("SELECT * FROM comment WHERE id = #{id}")
    Comment selectById(Integer id);

    // 根据用户ID查询评论
    @Select("SELECT * FROM comment WHERE user_id = #{userId}")
    List<Comment> selectByUserId(Integer userId);

    // 根据帖子ID查询评论
    @Select("SELECT * FROM comment WHERE post_id = #{postId}")
    List<Comment> selectByPostId(Integer postId);

    // 获取所有评论
    @Select("SELECT * FROM comment")
    List<Comment> selectAllComments();

    // 添加评论
    @Insert("INSERT INTO comment (user_id, post_id, content, show) " +
            "VALUES (#{userId}, #{postId}, #{content}, #{show})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void addComment(Comment comment);

    // 删除评论
    @Delete("DELETE FROM comment WHERE id = #{id}")
    void deleteById(Integer id);

    // 更新评论
    @Update("UPDATE comment SET " +
            "user_id = #{userId}, " +
            "post_id = #{postId}, " +
            "content = #{content}, " +
            "show = #{show} " +
            "WHERE id = #{id}")
    int updateComment(Comment comment);
}