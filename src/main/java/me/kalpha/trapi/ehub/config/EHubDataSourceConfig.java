package me.kalpha.trapi.ehub.config;

import com.zaxxer.hikari.HikariDataSource;
import me.kalpha.trapi.common.Constants;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "ehubEntityManagerFactory",
        transactionManagerRef = "ehubTransactionManager",
        basePackages = {"me.kalpha.trapi.ehub.repository"}//repositories
)
@EnableTransactionManagement
public class EHubDataSourceConfig {
    @Bean
    @ConfigurationProperties("spring.datasource.ehub")
    public DataSourceProperties ehubDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.ehub.hikari")
    public HikariDataSource ehubDataSource() {
        return ehubDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Bean(name = "ehubEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean ehubEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabasePlatform("org.hibernate.dialect.PostgreSQLDialect");
        vendorAdapter.setGenerateDdl(true);
        vendorAdapter.setShowSql(true);

        Properties properties = new Properties(); // Properties에 Hibernate Config 설정 추가
        properties.setProperty("hibernate.format_sql", String.valueOf(true));

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPersistenceUnitName(Constants.EHUB_UNIT_NAME); // EntitiManager 직접 사용을 위한 Name 설정
        factory.setPackagesToScan("me.kalpha.trapi.ehub.entity");
        factory.setDataSource(ehubDataSource());
        factory.setJpaProperties(properties);

        return factory;
    }

    @Bean
    public PlatformTransactionManager ehubTransactionManager(
            final @Qualifier("ehubEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory);
        return txManager;
    }
}