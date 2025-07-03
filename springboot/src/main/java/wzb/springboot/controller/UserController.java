package wzb.springboot.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import wzb.springboot.common.*;
import wzb.springboot.entity.Account;
import wzb.springboot.entity.User;
import wzb.springboot.mapper.UserMapper;


@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    UserMapper userMapper;

    @RequestMapping("/reg")
    public Result submit(@RequestBody User user) {
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        User reg_user = userMapper.selectByUsername(user.getUsername());
        if (Objects.isNull(reg_user))  // 用户名未注册
        {
            reg_user = new User();
            reg_user.setUsername(user.getUsername());
            reg_user.setPassword(user.getPassword());
            reg_user.setRole(Role.USER.role);   // 默认设置为普通用户
            userMapper.addUser(reg_user);
            return Result.success();
        }
        else {
            return Result.error(ResultCodeEnum.USER_HAD_EXIT);
        }
    }

    @RequestMapping("/login")
    public Result login(@RequestBody User user) throws Exception {
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());

        if (userMapper.selectByUsername(user.getUsername()) != null)    // 存在用户
        {
            User loginUser = userMapper.selectByUsername(user.getUsername());
            if (loginUser.getPassword().equals(user.getPassword()))    //密码正确
            {
                String token = TokenUtils.createToken(loginUser);
                Account account = new Account(
                        loginUser.getId(),
                        loginUser.getUsername(),
                        loginUser.getRole(),
                        token);
                return Result.success(account);
            }
            else    // 密码错误
            {
                return Result.error(ResultCodeEnum.PWD_ERROR);
            }
        }
        else    // 用户不存在
        {
            return Result.error(ResultCodeEnum.USER_DONT_EXIT);
        }
    }

    @RequestMapping("/getAll")
    public Result load() {
        List<User> users = userMapper.selectAllUser();
        System.out.println(users);
        return Result.success(users);
    }
    @RequestMapping("/getInfo/{id}")
    public Result load_one(@PathVariable Integer id) {
        User user = userMapper.selectById(id);
        System.out.println(user);
        return Result.success(user);
    }


    @RequestMapping("/update")
    public Result update(@RequestBody User user) {
        int suss = userMapper.updateUser(user);
        return Result.success(suss);
    }

    @RequestMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        userMapper.deleteById(id);
        return Result.success();
    }
}
