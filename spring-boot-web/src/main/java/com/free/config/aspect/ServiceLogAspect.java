package com.free.config.aspect;

import com.alibaba.fastjson.JSON;
import com.free.code.utils.IpAddrUtils;
import com.free.code.utils.RequestWrapper;
import com.free.dao.model.ServerLog;
import com.free.service.system.ServerLogService;
import com.free.utils.DateUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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

    @Before(value = "controllerAspect()")
    public void methodBefore(JoinPoint joinPoint){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        //打印请求内容
        logger.info("请求地址:"+request.getRequestURL().toString());
        logger.info("请求方式:"+request.getMethod());
        logger.info("请求类方法:"+joinPoint.getSignature());
        try {
            RequestWrapper requestWrapper = new RequestWrapper(request);
            String body = requestWrapper.getBody();
            logger.info("请求类方法参数 : \n" + body);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterReturning(returning = "resultObject",pointcut = "controllerAspect()")
    public void methodAfterReturing(Object resultObject){
        logger.info("Response内容:"+ JSON.toJSONString(resultObject));
    }

    @After("controllerAspect()")
    public void doAfter(JoinPoint point) {
        try {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = requestAttributes.getRequest();
            // 计算接口耗时
            Date startTime = DateUtils.parse(DateUtils.DATE_TIME_PATTERN,
                    String.valueOf(request.getAttribute("startTime")));
            long consumingTime = new Date().getTime() - startTime.getTime();
            // 获取请求的body
            RequestWrapper requestWrapper = new RequestWrapper(request);
            String body = requestWrapper.getBody();

            ServerLog serverLog = new ServerLog();
            serverLog.setClientIp(IpAddrUtils.getIpAddr(request));
            serverLog.setServerIp(IpAddrUtils.getServerIp());
            serverLog.setServerPost(String.valueOf(request.getLocalPort()));
            serverLog.setRequestMethod(request.getMethod());
            serverLog.setRequestUrl(request.getRequestURI());
            serverLog.setRequestParam(JSON.toJSONString(body));
            serverLog.setConsumingTime(consumingTime);
            serverLog.setResponseBody(point.getArgs().toString());

            logger.info(" Thread is id : " , Thread.currentThread().getId());
            logger.info(" Thread is name : " , Thread.currentThread().getName());
            serverLogService.saveServerLog(serverLog);
        } catch (Exception ex) {
            logger.info(">>>>>>>>>>>>>>> 记录日志到数据库异常 <<<<<<<<<<<<<");
        }
    }
//
//    @AfterThrowing
//    public void doAfterThrowing() {
//
//    }

}
