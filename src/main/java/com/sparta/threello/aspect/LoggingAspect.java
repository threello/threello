package com.sparta.threello.aspect;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j(topic = "Log 생성 AOP")
public class LoggingAspect {

    private final HttpServletRequest request;

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void logBefore() {
        if (request == null) {
            log.warn("RequestAttributes가 NULL입니다.");
        }

        String method = request.getMethod();
        String url = request.getRequestURL().toString();
        log.info("Request URL : {}, HTTP METHOD : {}", url, method);
    }

    @AfterReturning(pointcut = "controller()", returning = "result")
    public void logAfter(Object result) {
        log.info("Response : {}", result);
    }
}