package com.virgo.spring.security.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author HZI.HUI
 * @version 1.0
 * @since 2021/4/18
 */
@Configuration
public class RestTemplateConfig {



    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
