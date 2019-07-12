package com.coolcloud.sacw;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.logging.slf4j.Slf4jImpl;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.github.pagehelper.PageInterceptor;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan(lazyInit = true)
@MapperScan(basePackageClasses = Application.class)
@EnableTransactionManagement
@EnableAsync
@PropertySource("classpath:application.properties")
public class Application implements EnvironmentAware {

    private Environment environment;

    @Bean
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        return configurer;
    }

    @Bean
    public DataSource dataSource() {
        final String driverClassName = environment.getProperty("spring.datasource.driver-class-name");
        final String url = environment.getProperty("spring.datasource.url");
        final String username = environment.getProperty("spring.datasource.username");
        final String password = environment.getProperty("spring.datasource.password");

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setMinimumIdle(1);
        return dataSource;
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource());
        return transactionManager;
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory() {
        final boolean useGeneratedKeys = environment.getProperty("mybatis.use-generated-keys", Boolean.class);
        final boolean cacheEnabled = environment.getProperty("mybatis.cache-enabled", Boolean.class);
        final boolean mapUnderscoreToCamelCase = environment.getProperty("mybatis.map-underscore-to-camel-case", Boolean.class);
        final String helperDialect = environment.getProperty("pagehelper.helper-dialect");

        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setLogImpl(Slf4jImpl.class);
        configuration.setUseGeneratedKeys(useGeneratedKeys);
        configuration.setCacheEnabled(cacheEnabled);
        configuration.setMapUnderscoreToCamelCase(mapUnderscoreToCamelCase);

        PageInterceptor interceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("helperDialect", helperDialect);
        interceptor.setProperties(properties);

        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource());
        factory.setConfiguration(configuration);
        factory.setPlugins(new Interceptor[] { interceptor });

        return factory;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

}
