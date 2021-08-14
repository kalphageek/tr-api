package me.kalpha.trapi.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

/**
 * 인증토큰이 있는지 확인하고, 리소스 접근을 제한한다
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        //clientId
        resources.resourceId("eqp1Tr");
    }

    /**
     * antMatchers 와 mvcMatchers의 차이
     * antMatchers("/secured")는 정확한 /secured URL 과만 일치
     * mvcMatchers("/secured")는 /secured, /secured/, /secured.html와 일치
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .anonymous() // anonymous를 허용한다
                    .and()
                .authorizeRequests()
                    .mvcMatchers(HttpMethod.GET, "/v1/**").permitAll() // /v1/* 아래의 GET 호출은 anonymous
                    .anyRequest().authenticated() // 그 외는 인증을 필요로 한다
                    .and()
                .exceptionHandling()
                    .accessDeniedHandler(new OAuth2AccessDeniedHandler()); //access거부에 대해서는 OAuth2 handler를 사용한다 (403 status)
    }
}
