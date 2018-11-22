package com.lt;

import com.lt.config.AppConfig;
import com.lt.config.RootConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * web容器启动的时候创建对象，调用方法来初始化容器以前的前端控制器
 */
public class MyWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {


    /**
     * 获取根容器的配置类（Spring 的配置文件） 父容器
     * @return
     */
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{RootConfig.class};
    }

    /**
     * 获取web 容器的配置类（SpringMVC的配置文件） 子容器
     * @return
     */
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{AppConfig.class};
    }

    /**
     * 获取DispatcherServlet的映射信息
     *  /: 拦截所有请求（包括静态资源（xx.js, xx.png））,但是不包括 *.jsp
     *  /* : 拦截所有请求:连 *.jsp页面都拦截 jsp 页面是tomcat 的jsp 引擎解析的
     * @return
     */
    protected String[] getServletMappings() {


        return new String[]{"/"};
    }
}
