package lv.ez.weather.config;

import lv.ez.weather.exception.HeaderHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final HeaderHandlerInterceptor headerHandlerInterceptor;

    @Autowired
    public WebMvcConfig(HeaderHandlerInterceptor headerHandlerInterceptor) {
        this.headerHandlerInterceptor = headerHandlerInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(headerHandlerInterceptor).addPathPatterns("/**");
    }
}
