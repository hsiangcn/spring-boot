package com.free.config.filter;

import com.free.utils.DateUtils;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@Order(1)
@WebFilter(filterName = "serviceLogFilter", urlPatterns = "/*")
public class ServiceLogFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setAttribute("startTime", DateUtils.getCurrentDate(DateUtils.DATE_TIME_PATTERN));
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
