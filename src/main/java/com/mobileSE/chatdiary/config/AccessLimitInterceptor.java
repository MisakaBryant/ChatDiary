package com.mobileSE.chatdiary.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class AccessLimitInterceptor implements HandlerInterceptor {

    @Resource
    private StringRedisTemplate stringRedisTemplate ;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 只针对@Controller(RequestMappingHandlerMapping)的接口
        if (handler instanceof HandlerMethod handlerMethod) {
            Method method = handlerMethod.getMethod() ;
            // 具备AccessLimit注解的才进行拦截
            AccessLimit accessLimit = method.getDeclaredAnnotation(AccessLimit.class) ;
            if (accessLimit != null) {
                // 获取注解配置的参数
                long seconds = accessLimit.seconds() ;
                int count = accessLimit.count() ;
                if (seconds > 0 && count >= 0) {
                    String key = request.getRemoteAddr() + ":" + request.getRequestURI() ;
                    String value = this.stringRedisTemplate.opsForValue().get(key) ;
                    System.out.println("当前为：" + value) ;
                    if (value == null) {
                        this.stringRedisTemplate.opsForValue().set(key, String.valueOf(count - 1) , seconds, TimeUnit.SECONDS) ;
                        return true ;
                    } else {
                        int c = Integer.valueOf(value) ;
                        if (c <= 0) {
                            response.setContentType("application/json;charset=utf-8") ;
                            Map<String, Object> res = Map.ofEntries(
                                    Map.entry("code", -1),
                                    Map.entry("message", "访问太快了")
                            ) ;
                            response.getWriter().println(new ObjectMapper().writeValueAsString(res)) ;
                            return false ;
                        } else {
                            this.stringRedisTemplate.opsForValue().decrement(key) ;
                            return true ;
                        }
                    }
                }
            }
        }
        return true ;
    }

}