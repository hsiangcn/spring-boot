package com.free.config.aspect;

import com.alibaba.fastjson.JSON;
import com.free.code.utils.IpAddrUtils;
import com.free.code.utils.RequestWrapper;
import com.free.code.utils.lang.StringUtils;
import com.free.constant.ServiceLogConstant;
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
        request.setAttribute("startTime", System.currentTimeMillis());
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
        try {
            ServerLog serverLog = buildServerLog();
            serverLog.setExecuteStatus(ServiceLogConstant.EXECUTE_STATUS_SUCCESS.getCode());
            serverLog.setResponseBody(JSON.toJSONString(resultObject));
            serverLogService.saveServerLog(serverLog);
        } catch (Exception ex) {
            logger.error(" 日志记录数据库异常 ");
        }
    }

    @After("controllerAspect()")
    public void doAfter(JoinPoint point) {
        logger.info("Response内容:" + point.getArgs().toString());
    }

    @AfterThrowing(pointcut = "controllerAspect()", throwing = "e")
    public void doAfterThrowing(JoinPoint point, Throwable e) {
        try {
            ServerLog serverLog = buildServerLog();
            serverLog.setExecuteStatus(ServiceLogConstant.EXECUTE_STATUS_FAILED.getCode());
            serverLog.setResponseBody(e.getMessage());
            serverLogService.saveServerLog(serverLog);
        } catch (Exception ex) {
            logger.error(" 异常日志记录数据库异常 ");
        }
    }

    private ServerLog buildServerLog() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        // 计算接口耗时
        long startTime = Long.parseLong(String.valueOf(request.getAttribute("startTime")));
        long consumingTime = System.currentTimeMillis() - startTime;
        // 获取请求的body
        String body = null;
        try {
            RequestWrapper requestWrapper = new RequestWrapper(request);
            body = requestWrapper.getBody();
        } catch (IOException e) {
            e.printStackTrace();
            body = "获取请求参数异常";
        }
        ServerLog serverLog = new ServerLog();
        serverLog.setClientIp(IpAddrUtils.getIpAddr(request));
        serverLog.setServerIp(IpAddrUtils.getServerIp());
        serverLog.setServerPost(String.valueOf(request.getLocalPort()));
        serverLog.setRequestMethod(request.getMethod());
        serverLog.setRequestUrl(request.getRequestURI());
        serverLog.setRequestParam(body);
        serverLog.setConsumingTime(consumingTime);
        return serverLog;
    }

}
