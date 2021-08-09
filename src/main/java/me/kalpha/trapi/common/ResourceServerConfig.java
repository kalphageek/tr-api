package me.kalpha.trapi.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("eqp1Tr");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .anonymous() // anonymous를 허용한다
                    .and()
                .authorizeRequests()
                    .mvcMatchers(HttpMethod.GET, "/v1/**").anonymous() // /v1/* 아래의 GET 호출은 anonymous
                    .anyRequest().authenticated() // 그 외는 인증을 필요로 한다
                    .and()
                .exceptionHandling()
                    .accessDeniedHandler(new OAuth2AccessDeniedHandler()); //access거부에 대해서는 OAuth2 handler를 사용한다 (403 status)
    }
}
