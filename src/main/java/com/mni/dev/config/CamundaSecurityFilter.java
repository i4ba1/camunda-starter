package com.mni.dev.config;

import org.camunda.bpm.engine.rest.security.auth.ProcessEngineAuthenticationFilter;
import org.camunda.bpm.engine.rest.security.auth.impl.HttpBasicAuthenticationProvider;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

public class CamundaSecurityFilter {
    @Bean
    public FilterRegistrationBean<ProcessEngineAuthenticationFilter> processEngineAuthenticationFilter() {
        FilterRegistrationBean<ProcessEngineAuthenticationFilter> registration = new FilterRegistrationBean<>();
        registration.setName("camunda-auth");
        registration.setFilter(new ProcessEngineAuthenticationFilter());
        registration.addInitParameter("authentication-provider", HttpBasicAuthenticationProvider.class.getName());
        registration.addUrlPatterns("/*");
        return registration;
    }
}
