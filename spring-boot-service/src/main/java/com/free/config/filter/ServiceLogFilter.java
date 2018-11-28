//package com.free.config.filter;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.core.annotation.Order;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import java.io.IOException;
//
////@Order(1)
////@WebFilter(filterName = "serviceLogFilter", urlPatterns = "/*")
//public class ServiceLogFilter implements Filter {
//    private static final Logger logger = LoggerFactory.getLogger(ServiceLogFilter.class);
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        servletRequest.setAttribute("startTime", System.currentTimeMillis());
//        filterChain.doFilter(servletRequest, servletResponse);
//    }
//}
