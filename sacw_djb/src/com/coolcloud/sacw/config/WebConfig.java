package com.coolcloud.sacw.config;

import java.util.Properties;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter implements EnvironmentAware {

    private Environment environment;

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public BeanNameViewResolver beanNameViewResolver() {
        BeanNameViewResolver resolver = new BeanNameViewResolver();
        resolver.setOrder(0);
        return resolver;
    }

    @Bean
    public FreeMarkerViewResolver freeMarkerViewResolver() {
        final String suffix = environment.getProperty("spring.freemarker.suffix", ".ftl");
        final String contentType = environment.getProperty("spring.freemarker.content-type", "text/html;charset=utf-8");
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        resolver.setSuffix(suffix);
        resolver.setContentType(contentType);
        return resolver;
    }

    @Bean
    public MultipartResolver multipartResolver() {
        StandardServletMultipartResolver resolver = new StandardServletMultipartResolver();
        return resolver;
    }

    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer() {
        final String templateLoaderPath = environment.getProperty("spring.freemarker.template-loader-path", "/WEB-INF/ftl/");
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setTemplateLoaderPath(templateLoaderPath);
        Properties settings = new Properties();
        settings.setProperty("defaultEncoding", "utf-8");
        configurer.setFreemarkerSettings(settings);
        return configurer;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

}
