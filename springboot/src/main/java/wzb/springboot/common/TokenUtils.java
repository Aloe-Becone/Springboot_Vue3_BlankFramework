package wzb.springboot.common;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import wzb.springboot.entity.Account;
import wzb.springboot.entity.User;
import wzb.springboot.mapper.UserMapper;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Token工具类
 */
@Component
public class TokenUtils {
    /**
     * 生成token
     */
    public static String createToken(User user)
    {
        return JWT.create().withAudience(String.valueOf(user.getId())) // 将 id 保存到 token 里面,作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(), 1)) // 1小时后token过期
                .sign(Algorithm.HMAC256(user.getRole() + user.getPassword())); // 以 role + password 作为 token 的密钥
    }


    /**
     * 获取当前登录的用户信息
     */
    public static User getCurrentUser() {
        UserMapper userMapper = null;
        User user = null;
        try {
            HttpServletRequest request = (HttpServletRequest) ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            if (ObjectUtil.isNotEmpty(token)) {
                int userId = Integer.parseInt(JWT.decode(token).getAudience().get(0));
                user = userMapper.selectById(userId);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return user;
    }
}

