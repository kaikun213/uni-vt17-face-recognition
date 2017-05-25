package com.faceRecognition.faceRecognition_api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http
    		.csrf().disable()
    		.exceptionHandling().authenticationEntryPoint(new RestAuthenticationEntryPoint())
    		.and()
            .authorizeRequests()
                .antMatchers("/admin").hasRole("ADMIN")
                .and()
            .authorizeRequests()
            	.antMatchers("/user").hasRole("USER")
            	.and()
            .httpBasic();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
                .withUser("admin").password("password").roles("ADMIN").and()
                .withUser("user").password("password").roles("USER");
    }
    
    /**
     * Instead of redirecting to a login page we send "unauthorized" back if tries to access any not existing resource or without role
     */
    @Component
    public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
     
        @Override
        public void commence(HttpServletRequest arg0, HttpServletResponse arg1,
                AuthenticationException arg2) throws IOException, ServletException {
            arg1.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
     
        }
     
    }
}
