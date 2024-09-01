package com.eduardoschelive.deliverbills.common.config;

import com.eduardoschelive.deliverbills.filter.resolver.FilterEndpointPageableResolver;
import com.eduardoschelive.deliverbills.filter.resolver.FilterEndpointSpecificationResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedOrigins("*");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new FilterEndpointPageableResolver());
        resolvers.add(new FilterEndpointSpecificationResolver());
    }

}
