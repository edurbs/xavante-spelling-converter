package com.edurbs.xavantespellingconverter.core.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.edurbs.xavantespellingconverter.core.properties.Properties;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private Properties properties;

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurator) {
        configurator.defaultContentType(MediaType.APPLICATION_JSON);

    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/convert")
                .allowedOrigins(properties.getHostname());    }
    
}
