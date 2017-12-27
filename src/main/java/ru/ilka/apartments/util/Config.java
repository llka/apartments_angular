package ru.ilka.apartments.util;

import oracle.jdbc.pool.OracleDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.ilka.apartments.handler.AuthFailureHandler;
import ru.ilka.apartments.logic.UserLogic;

import javax.sql.DataSource;
import java.sql.SQLException;


@Configuration
@EnableWebMvc
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
    public AuthFailureHandler authFailureHandler(){
        return new AuthFailureHandler();
    }
}
