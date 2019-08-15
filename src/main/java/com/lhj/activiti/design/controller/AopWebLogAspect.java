package com.lhj.activiti.design.controller;

import com.alibaba.fastjson.JSONObject;
import com.lhj.activiti.design.utils.JsonUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
public class AopWebLogAspect {
    private final static Logger logger = LoggerFactory.getLogger(AopWebLogAspect.class);
    private static final JsonUtils jsonUtils = JsonUtils.getInstance();
    /**
     * 以Controller包下定义所有请求的方法
     */
    @Pointcut("execution(* com.lhj.activiti.design.controller..*(..))")
    public void controller() {
    }

    /**
     * 以Service包下定义所有请求的方法
     */
    @Pointcut("execution(* com.lhj.activiti.design.service..*(..))")
    public void service() {
    }
    /**
     * 在切入点之前进行
     */
    @Before("controller()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(attributes != null){
            HttpServletRequest request = attributes.getRequest();
            logger.info("========================================== Start ==========================================");
            //打印请求参数相关日志
            // 打印请求 url
            logger.info("url:{}", request.getRequestURI().toString());
            // 打印 Http method
            logger.info("HTTP Method:{}", request.getMethod());
            // 打印调用 controller 的全路径以及执行方法
            logger.info("Class Method:{}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
            // 打印请求的 IP
            logger.info("IP :{}", request.getRemoteAddr());
            // 打印请求入参
            Object[] args = null;
            try {
                args = joinPoint.getArgs();
            } catch (Exception e) {
                logger.info("Request Args:{}","NULL");
            }
            logger.info("Request Args:{}", args != null ? jsonUtils.objectToJsonCustomize(args) : "NULL");
        }

    }

    /**
     * 在切入点之后执行
     */
    @After("controller()")
    public void doAfter() {
        logger.info("========================================== end ==========================================");
    }

    @Around("controller()")
    public Object doAroud(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = null;
        try {
            if(proceedingJoinPoint != null){
                result = proceedingJoinPoint.proceed();
            }
        } catch (Throwable throwable) {
            logger.info("Response Args : {}", "NULL");
        }
        //打印出参
        logger.info("Response Args : {}", result != null ? jsonUtils.objectToJsonCustomize(result) : "NULL");
        // 执行耗时
        logger.info("Time-Consuming : {} ms", System.currentTimeMillis() - startTime);
        return result;
    }

    @Before("service()")
    public void service(JoinPoint point) {
        logger.info("\n\tservice method: {}", getMethodInfo(point));
    }

    private String getMethodInfo(JoinPoint point) {
        ConcurrentHashMap<String, Object> info = new ConcurrentHashMap<>(3);

        info.put("class", point.getTarget().getClass().getSimpleName());
        info.put("method", point.getSignature().getName());

        String[] parameterNames = ((MethodSignature) point.getSignature()).getParameterNames();
        ConcurrentHashMap<String, String> args = null;

        if (Objects.nonNull(parameterNames)) {
            args = new ConcurrentHashMap<>(parameterNames.length);
            for (int i = 0; i < parameterNames.length; i++) {
                String value = point.getArgs()[i] != null ? point.getArgs()[i].toString() : "null";
                args.put(parameterNames[i], value);
            }
            info.put("args", args);
        }

        return JSONObject.toJSONString(info);
    }

}
