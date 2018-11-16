package com.free.config.jetty;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.DispatcherServlet;

@SpringBootApplication
@ServletComponentScan("com.free.config.*")
@MapperScan(basePackages = {"com.free.dao"})
@ComponentScan(basePackages = {
        "com.free.service", "com.free.utils",
        "com.free.contoller.*","com.free.config.*"})
public class SpringBootWebApplication {


    public static void main(String[] args) {
        SpringApplication.run(SpringBootWebApplication.class, args);
    }

    /**
     *   配置URL后缀
     *
     * @param dispatcherServlet
     * @return
     */
    @Bean
    public ServletRegistrationBean servletRegistrationBean(DispatcherServlet dispatcherServlet) {
        ServletRegistrationBean<DispatcherServlet> servletServletRegistrationBean = new ServletRegistrationBean<>(dispatcherServlet);
        servletServletRegistrationBean.addUrlMappings("*.htm");
        return servletServletRegistrationBean;
    }
}
