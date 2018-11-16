package com.free.config.aspect;

import com.alibaba.fastjson.JSON;
import com.free.dao.model.ServerLog;
import com.free.service.system.ServerLogService;
import com.free.utils.DateUtils;
import com.free.utils.IpAddrUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.Date;

@Aspect
@Component
public class ServiceLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(ServiceLogAspect.class);

    @Autowired(required = false)
    private ServerLogService serverLogService;

    /**
     * 使用@Pointcut注解声明频繁使用的切入点表达式
     */
    @Pointcut("execution(public * com.free.contoller.user.*.*(..))")
    public void controllerAspect(){

    }

    //请求method前打印内容
    @Before(value = "controllerAspect()")
    public void methodBefore(JoinPoint joinPoint){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        //打印请求内容
        logger.info(">>>>>>>>>>>>>>> 请求内容  start <<<<<<<<<<<<<<<");
        logger.info("请求地址:"+request.getRequestURL().toString());
        logger.info("请求方式:"+request.getMethod());
        logger.info("请求类方法:"+joinPoint.getSignature());
        logger.info("请求类方法参数:"+ JSON.toJSONString(joinPoint.getArgs()));
        logger.info(">>>>>>>>>>>>>>> 请求内容  end <<<<<<<<<<<<<<<");
    }
    //在方法执行完结后打印返回内容
    @AfterReturning(returning = "resultObject",pointcut = "controllerAspect()")
    public void methodAfterReturing(Object resultObject){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        Date startTime = DateUtils.parse(DateUtils.DATE_TIME_PATTERN,
                String.valueOf(request.getAttribute("startTime")));
        long consumingTime = new Date().getTime() - startTime.getTime();
        ServerLog serverLog = new ServerLog();

        serverLog.setClientIp(IpAddrUtils.getIpAddr(request));
        serverLog.setServerIp(IpAddrUtils.getServerIp());
        serverLog.setServerPost(String.valueOf(request.getLocalPort()));
        serverLog.setRequestMethod(request.getMethod());
        serverLog.setRequestUrl(request.getRequestURI());
        serverLog.setRequestParam(JSON.toJSONString(request.getContextPath()));
        serverLog.setConsumingTime(consumingTime);
        serverLog.setResponseBody(JSON.toJSONString(resultObject));

        serverLogService.saveServerLog(serverLog);
        logger.info(">>>>>>>>>>>>>>> 返回内容 <<<<<<<<<<<<<<<");
        logger.info("server log 内容:"+ JSON.toJSONString(serverLog));
        logger.info("Response内容:"+ JSON.toJSONString(resultObject));
        logger.info(">>>>>>>>>>>>>>> 返回内容 <<<<<<<<<<<<<<<");
    }

}
