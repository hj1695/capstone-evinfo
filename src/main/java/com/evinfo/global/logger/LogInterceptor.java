package com.evinfo.global.logger;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j(topic = "HTTP_FILE_LOGGER")
@Component
public class LogInterceptor implements AsyncHandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("url : {}", request.getRequestURL());
        log.info("request : {} response : {}", request.getMethod(), response.getStatus());

        return true;
    }
}
