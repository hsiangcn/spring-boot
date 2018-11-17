package com.free.config.filter;

import com.free.code.utils.RequestWrapper;
import com.free.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(1)
@WebFilter(filterName = "serviceLogFilter", urlPatterns = "/*")
public class ServiceLogFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(ServiceLogFilter.class);
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setAttribute("startTime", DateUtils.getCurrentDate(DateUtils.DATE_TIME_PATTERN));
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
