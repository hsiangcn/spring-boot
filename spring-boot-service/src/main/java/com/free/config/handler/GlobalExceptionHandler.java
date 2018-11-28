package com.free.config.handler;

import com.alibaba.fastjson.JSON;
import com.free.code.constant.ResultSuccess;
import com.free.code.utils.ResultMessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpStatus.NOT_EXTENDED;

/**
 * @ClassName GlobalExceptionHandler
 * @Description 在执行@RequestMapping时，进入逻辑处理阶段前。譬如传的参数类型错误。
 * @Author hsiangcn@sina.com
 * @Date 2018/11/28 14:53
 * @Version 1.0
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 系统异常处理，比如：404,500
     *
     * @param req
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public String defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        if (e instanceof org.springframework.web.servlet.NoHandlerFoundException) {
            // 404
            return JSON.toJSONString(ResultMessageUtils.result(ResultSuccess.INTERFACE_OUTER_INVOKE_ERROR));
        } else {
            // 500
            return JSON.toJSONString(ResultMessageUtils.result(ResultSuccess.INTERFACE_INNER_INVOKE_ERROR));
        }
    }
}