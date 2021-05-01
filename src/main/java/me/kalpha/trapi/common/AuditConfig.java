package me.kalpha.trapi.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

/**
 * 자동으로 createBy, lastModifiedBy 를 설정해준다
 */
@EnableJpaAuditing
@Configuration
public class AuditConfig {
    @Bean
    AuditorAware<String> auditorAware() {
        // Session으로 부터 User ID를 파라미터로 넘긴다.
        return () -> Optional.of("2043738");
    }
}
