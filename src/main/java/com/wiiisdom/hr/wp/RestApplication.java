package com.wiiisdom.hr.wp;

import org.glassfish.jersey.server.ResourceConfig;

import com.wiiisdom.hr.wp.providers.ValidationExceptionMapper;

import jakarta.ws.rs.ApplicationPath;

@ApplicationPath("/rest")
public class RestApplication extends ResourceConfig {

    public RestApplication configureApplication() {
        packages("com.wiiisdom.hr.wp.resources");
        register(ValidationExceptionMapper.class);
        return this;
    }
}
