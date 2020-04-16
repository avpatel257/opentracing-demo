package com.two57.opentracing.serviceb;

import feign.okhttp.OkHttpClient;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
    @Import(FeignClientsConfiguration.class)
public class Config {
    @Bean
    public OkHttpClient client() {
        return new OkHttpClient();
    }
}