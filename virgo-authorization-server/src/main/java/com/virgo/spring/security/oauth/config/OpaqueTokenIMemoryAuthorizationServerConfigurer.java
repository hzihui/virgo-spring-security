package com.virgo.spring.security.oauth.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import java.util.Collection;
import java.util.HashMap;

/**
 * @author HZI.HUI
 * @since 2021/4/16
 */
@Slf4j
@Configuration
@EnableAuthorizationServer
@RequiredArgsConstructor
public class OpaqueTokenIMemoryAuthorizationServerConfigurer extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManagerBean;
    private final PasswordEncoder passwordEncoder;

    /**
     * 授权服务器安全配置
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .allowFormAuthenticationForClients()
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    /**
     * 客户端配置
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        HashMap<String, String> appInfo = new HashMap<>();
        appInfo.put("url","https://www.baidu.com");
        appInfo.put("name","百度");
        clients.inMemory()
                .withClient("test")
                .secret(passwordEncoder.encode("test"))
                .scopes("read","writer")
                .authorizedGrantTypes("password","refresh_token","client_credentials")
                .and()
                .withClient("baidu")
                .secret(passwordEncoder.encode("baidu"))
                .scopes("read","writer")
                .additionalInformation(appInfo)
                .redirectUris("http://localhost:3000/oauth/callback")
                .autoApprove(true)
                .authorizedGrantTypes("authorization_code","refresh_token","implicit");
    }


    /**
     * 授权服务端点配置
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .allowedTokenEndpointRequestMethods(HttpMethod.POST,HttpMethod.GET)
                .authenticationManager(authenticationManagerBean)
                .tokenStore(tokenStore())
                .userDetailsService(userDetailsService())
                .pathMapping("/oauth/confirm_access","/auth/confirm");
    }


    @Bean
    public UserDetailsService userDetailsService(){
        return s -> new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return AuthorityUtils.createAuthorityList("roleA","roleB","roleC");
            }

            @Override
            public String getPassword() {
                return passwordEncoder.encode("123456");
            }

            @Override
            public String getUsername() {
                return "user";
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };
    }


    @Bean
    public TokenStore tokenStore(){
        return new InMemoryTokenStore();
    }

}
