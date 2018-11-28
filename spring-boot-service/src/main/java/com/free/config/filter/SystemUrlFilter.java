package com.free.config.filter;

import com.alibaba.fastjson.JSON;
import com.free.code.constant.ResultSuccess;
import com.free.code.utils.RequestWrapper;
import com.free.code.utils.ResultMessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;

@Order(2)
@WebFilter(filterName = "systemUrlFilter", urlPatterns = "/*")
public class SystemUrlFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(SystemUrlFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        RequestWrapper requestWrapper = new RequestWrapper(request);
        String url = requestWrapper.getRequestURI();
        String urlLength = url.substring(url.length()-4,url.length());
        if (".htm".equals(urlLength)) {
            filterChain.doFilter(requestWrapper, servletResponse);
        } else {
            servletResponse.setCharacterEncoding("UTF-8");
            PrintWriter writer = servletResponse.getWriter();
            writer.write(JSON.toJSONString(ResultMessageUtils.result(ResultSuccess.INTERFACE_SUFFIX_INVALID)));
            writer.flush();
        }
    }
}
