package com.coolcloud.sacw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.coolcloud.sacw.security.CompositeAuthenticationSuccessHandler;
import com.coolcloud.sacw.security.LogoutSuccessHandlerImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] IGNORES = { "/**/favicon.ico", "/css/**", "/js/**", "/assets/**", "/img/**", //
            "/overdue/waring", "/html/1020.mp3", "/warning/count" };

    private static final String LOGIN_PAGE = "/html/login.html";

    private static final String LOGIN_PROCESSING_URL = "/system/login";

    private static final String LOGOUT_URL = "/system/logout";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http//
                .csrf().disable()//
                .authorizeRequests().antMatchers(IGNORES).permitAll()//
                .and().authorizeRequests().anyRequest().authenticated()//
                .and().formLogin().loginPage(LOGIN_PAGE).permitAll().loginProcessingUrl(LOGIN_PROCESSING_URL).permitAll().successHandler(new CompositeAuthenticationSuccessHandler())//
                .and().logout().logoutUrl(LOGOUT_URL).permitAll().logoutSuccessHandler(new LogoutSuccessHandlerImpl())//
                .and().headers().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
