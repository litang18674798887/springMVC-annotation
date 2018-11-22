package com.lt.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.*;

/**
 * SpringMVC 只扫描Controller 子容器
 * useDefaultFilters= false 禁用默认的过滤规则
 */
@EnableWebMvc
@ComponentScan(value = "com.lt",includeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION,classes = {Controller.class})
},useDefaultFilters = false)
public class AppConfig extends WebMvcConfigurerAdapter {


    /**
     * 定制视图解析器
     * @param registry
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {

        //默认所有的页面都从/WEB-INF/ xxx.jsp
//        registry.jsp();
        registry.jsp("/WEB-INF/views/",".jsp");
    }


    /**
     * 静态资源访问
     * @param configurer
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {

        configurer.enable();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
         registry.addInterceptor(new MyFirstInterceptor()).addPathPatterns("/**");


    }
}
