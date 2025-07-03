package wzb.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import wzb.springboot.common.JwtInterceptor;

import javax.annotation.Resource;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Resource
    private JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**") // 拦截所有路径
                .excludePathPatterns("/user/login") // 排除登录接口
                .excludePathPatterns("/user/register") // 排除注册接口
                .excludePathPatterns("/images/**") // 排除静态资源
                .excludePathPatterns("/error") // 排除错误页面
                .excludePathPatterns("/swagger-ui/**") // 排除Swagger
                .excludePathPatterns("/v3/api-docs/**"); // 排除API文档
    }
}