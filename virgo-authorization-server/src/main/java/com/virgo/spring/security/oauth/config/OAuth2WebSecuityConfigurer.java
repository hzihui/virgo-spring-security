package com.virgo.spring.security.oauth.config;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;


/**
 * @author HZI.HUI
 * @since 2021/4/16
 */
@Slf4j
@Order(100)
@Configuration
public class OAuth2WebSecuityConfigurer extends WebSecurityConfigurerAdapter {


    /**
     * web 安全配置
     * @param web
     */
    @Override
    public void configure(WebSecurity web){
        web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }


    /**
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .formLogin(login -> login
                        .loginPage("/auth/page")
                        .loginProcessingUrl("/auth/login")
                .failureHandler(authenticationFailureHandler()))
                .authorizeRequests(authorizeRequest -> authorizeRequest
                        .antMatchers("/auth/**").permitAll()
                        .anyRequest().authenticated());
    }


    /**
     * 注入认证管理器
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    /**
     * 密码编码方式
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    /**
     * 表单登录失败异常处理
     * @return
     */
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler(){
        return (request, response, e) -> {
            val message = e.getMessage();
            log.error("表单登录异常：{}",message);
            String url = HttpUtil.encodeParams(String.format("/auth/page?error=%s",message),
                    CharsetUtil.CHARSET_UTF_8);
            response.sendRedirect(url);
        };
    }

}
