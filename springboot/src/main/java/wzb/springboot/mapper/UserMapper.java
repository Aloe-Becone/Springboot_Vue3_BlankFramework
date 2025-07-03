package wzb.springboot.mapper;

import org.apache.ibatis.annotations.*;
import wzb.springboot.entity.User;
import java.util.List;

@Mapper
public interface UserMapper {
    // 根据id查询
    @Select("SELECT * FROM user WHERE id = #{id}")
    User selectById(Integer id);

    // 根据用户名查询
    @Select("SELECT * FROM user WHERE username = #{username}")
    User selectByUsername(String username);

    // 获取用户列表
    @Select("SELECT * FROM user")
    List<User> selectAllUser();

    // 添加用户
    @Insert("INSERT INTO user (username, password, role) VALUES (#{username}, #{password}, #{role})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void addUser(User user);

    // 删除用户
    @Delete("DELETE FROM user WHERE id = #{id}")
    void deleteById(@Param("id") Integer id);

    // 修改用户
    @Update("UPDATE user SET " +
            "name = #{name}," +
            "sex = #{sex}," +
            "school = #{school}," +
            "number = #{number}," +
            "major = #{major}," +
            "grade = #{grade}," +
            "phone = #{phone}," +
            "info = #{info} " +
            "WHERE id = #{id}")
    int updateUser(User user);
}
