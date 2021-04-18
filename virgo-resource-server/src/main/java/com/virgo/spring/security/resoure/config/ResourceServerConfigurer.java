package com.virgo.spring.security.resoure.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

/**
 *
 * 资源服务器配置信息
 * @author HZI.HUI
 * @version 1.0
 * @since 2021/4/17
 */
@Slf4j
@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class ResourceServerConfigurer extends WebSecurityConfigurerAdapter {

    /**
     * 认证相关、包括UserDetail、密码编码方式
     * @param auth 认证管理器
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }


    /**
     * Web 相关安全配置、常用来忽略静态文件的校验
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }


    /**
     * Http 安全相关配置、用来配置安全过滤器链
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement(AbstractHttpConfigurer::disable)
                .authorizeRequests(authReq -> authReq
                .anyRequest().authenticated())
                .oauth2ResourceServer(resourceServer -> resourceServer.opaqueToken()
                        .introspectionUri("http://localhost:1000/oauth/check_token")
                        .introspectionClientCredentials("test12","test"));
    }
}
