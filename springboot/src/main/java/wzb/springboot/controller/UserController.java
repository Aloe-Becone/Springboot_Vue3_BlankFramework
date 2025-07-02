package wzb.springboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import wzb.springboot.common.Result;
import wzb.springboot.common.ResultCodeEnum;
import wzb.springboot.common.Role;
import wzb.springboot.common.TokenUtils;
import wzb.springboot.entity.Account;
import wzb.springboot.entity.CommonRequest;
import wzb.springboot.entity.User;
import wzb.springboot.mapper.UserMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
            reg_user.setRole(Role.USER.role);
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
                        loginUser.getId(), loginUser.getUsername(),
                        loginUser.getRole(), token);
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


    @RequestMapping("/trolley")
    public Result trolley(@RequestParam("goodsId") Integer goodsId,
                          @RequestParam("userId")Integer userId)
    {
        List<Integer> list = new ArrayList<>();
        // 从数据库中获取用户的购物车数据
        String trolley = userMapper.selectTrolleyByUserId(userId);
        System.out.println(trolley);
        ObjectMapper mapper = new ObjectMapper();

        if (Objects.isNull(trolley)){
            list.add(goodsId);
            list.forEach(System.out::println);
        }
        else {
            try {
                // 直接将JSON数组字符串转换为List<Integer>
                list = mapper.readValue(trolley, new TypeReference<List<Integer>>() {});
                list.add(goodsId);
                list.forEach(System.out::println);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            String jsonArray = mapper.writeValueAsString(list);
            System.out.println(jsonArray);
            userMapper.updateTrolley(jsonArray, userId);
            return Result.success(list);
        } catch (Exception e) {
            return Result.error();
        }
    }


    @RequestMapping("/getTrolley")
    public Result getTrolley(@RequestParam("userId") Integer id) {
        String trolley = userMapper.selectTrolleyByUserId(id);
        return Result.success(trolley);
    }

    @RequestMapping("/getAll")
    public Result load() {
        List<User> users = userMapper.selectAllUser();
        System.out.println(users);
        return Result.success(users);
    }

    @RequestMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        userMapper.deleteById(id);
        return Result.success();
    }
}
