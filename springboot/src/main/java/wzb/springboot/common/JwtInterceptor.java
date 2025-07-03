package wzb.springboot.common;

import cn.hutool.core.util.ObjectUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import wzb.springboot.entity.User;
import wzb.springboot.mapper.UserMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * jwt拦截器
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(JwtInterceptor.class);

    UserMapper userMapper;
    User user;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        // 1. 从http请求的header中获取token
        String token = request.getHeader("token");
        if (ObjectUtil.isEmpty(token)) {
            // 如果没拿到，从参数里再拿一次
            token = request.getParameter("token");
        }
        System.out.println(token);

        // 2. 开始执行认证
        if (ObjectUtil.isEmpty(token))    // 空token时
        {
            System.out.println(ResultCodeEnum.TOKEN_INVALID_ERROR);
            return false;
        }
        else
        {
            // 解析token获取存储的数据
            int userId = Integer.parseInt(JWT.decode(token).getAudience().get(0));
            user = userMapper.selectById(userId);
        }

        if (ObjectUtil.isNull(user)) {
            System.out.println(ResultCodeEnum.TOKEN_INVALID_ERROR);
            return false;
        }
        try {
            // 用户密码加签验证 token
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getRole() + user.getPassword())).build();
            jwtVerifier.verify(token); // 验证token
        } catch (JWTVerificationException e) {
            System.out.println(ResultCodeEnum.TOKEN_CHECK_ERROR);
            return false;
        }
        return true;
    }
}