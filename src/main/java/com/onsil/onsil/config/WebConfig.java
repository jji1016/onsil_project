package com.onsil.onsil.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.path}products")
    String productsPath;

    @Value("${file.path}reviews")
    String reviewsPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/products/**")
                .addResourceLocations("file:///" + productsPath);
        registry.addResourceHandler("/upload/reviews/**")
                .addResourceLocations("file:///" + reviewsPath);
    }


}
