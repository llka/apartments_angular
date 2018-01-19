package ru.ilka.apartments.config;

import oracle.jdbc.pool.OracleDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.ilka.apartments.handler.AuthFailureHandler;
import ru.ilka.apartments.logic.UserLogic;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableSwagger2
@EnableWebMvc
@EnableCaching
@ComponentScan(value = "ru.ilka.apartments")
@PropertySource("classpath:application.properties")
public class Config {

    @Value("${spring.datasource.driver-class-name}")
    private String driver;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    @Value("${password-encoder.strength}")
    private int passwordStrength;

    @Bean
    DataSource dataSource() throws SQLException {
        OracleDataSource dataSource = new OracleDataSource();
        dataSource.setUser(username);
        dataSource.setPassword(password);
        dataSource.setURL(url);
        dataSource.setImplicitCachingEnabled(true);
        dataSource.setFastConnectionFailoverEnabled(true);
        return dataSource;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(passwordStrength);
        return bCryptPasswordEncoder;
    }

    @Bean
    public UserLogic userLogic() {
        return new UserLogic();
    }

    @Bean
    public AuthFailureHandler authFailureHandler() {
        return new AuthFailureHandler();
    }

    //http://www.baeldung.com/swagger-2-documentation-for-spring-rest-api
    //https://editor.swagger.io//?_ga=2.161732990.35370229.1515158885-1944002411.1515158885#/
    //http://localhost:8099/v2/api-docs
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("ru.ilka.apartments.controller"))
                .paths(PathSelectors.any())
                /*.paths(regex("/user.*"))*/
                /*.paths(regex("/apartment.*"))*/
                /*.paths(regex("/hotel.*"))*/
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Apartments Booking REST API",
                "Rest Api to manage and book apartments, developed with Spring (Boot, MVC, Security, JPA) , Angular 5, Oracle, Hibernate",
                "API TOS",
                "Terms of service",
                new Contact("Ilya Kisel", "https://github.com/llka/apartments_angular", "ilya_kisel@epam.com"),
                "License of API", "API license URL", Collections.emptyList());
    }

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(
                new ConcurrentMapCache("users"),
                new ConcurrentMapCache("apartments")));
        return cacheManager;
    }
}
