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
        clients.inMemory()
                .withClient("test")
                .secret(passwordEncoder.encode("test"))
                .scopes("test")
                .redirectUris("http://localhost:3000/oauth/callback")
                .authorizedGrantTypes("password","authorization_code","refresh_token","client_credentials");
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
                .userDetailsService(userDetailsService());
    }


    @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
                return new UserDetails() {
                    public Collection<? extends GrantedAuthority> getAuthorities() {
                        return AuthorityUtils.createAuthorityList("roleA","roleB","roleC");
                    }

                    public String getPassword() {
                        return passwordEncoder.encode("123456");
                    }

                    public String getUsername() {
                        return "user";
                    }

                    public boolean isAccountNonExpired() {
                        return true;
                    }

                    public boolean isAccountNonLocked() {
                        return true;
                    }

                    public boolean isCredentialsNonExpired() {
                        return true;
                    }

                    public boolean isEnabled() {
                        return true;
                    }
                };
            }
        };
    }


    @Bean
    public TokenStore tokenStore(){
        return new InMemoryTokenStore();
    }

}
