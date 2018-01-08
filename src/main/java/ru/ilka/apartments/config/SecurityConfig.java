package ru.ilka.apartments.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.ilka.apartments.handler.AuthFailureHandler;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@ComponentScan(value = "ru.ilka.apartments")
@PropertySource("classpath:application.properties")
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    private static Logger logger = LogManager.getLogger(SecurityConfig.class);

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthFailureHandler authFailureHandler;

    @Autowired
    private DataSource dataSource;

    @Value("${spring.queries.users-query}")
    private String usersQuery;

    @Value("${spring.queries.roles-query}")
    private String rolesQuery;

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.
                jdbcAuthentication()
                    .dataSource(dataSource)
                    .passwordEncoder(bCryptPasswordEncoder)
                    .usersByUsernameQuery(usersQuery)
                    .authoritiesByUsernameQuery(rolesQuery);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/","/start","/login","/register","/about","/swagger-ui.html","/swagger-ui","/v2/api-docs").permitAll()
                    .antMatchers("/admin").hasRole("ADMIN")
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/user","/user/**").hasAuthority("USER")
                    .antMatchers("/apartment","/apartment/**").hasAuthority("USER")
                    .antMatchers("/hotel","/hotel/**").hasAuthority("USER")
                    .anyRequest().authenticated()
                    .and()
                .csrf()
                    .disable()
                .formLogin()
                    .loginPage("/login")
                    .failureHandler(authFailureHandler)
                    .defaultSuccessUrl("/login?access=allow")
                    .usernameParameter("login")
                    .passwordParameter("password")
                    .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login?logout=true")
                    .invalidateHttpSession(true)
                    .and()
                .exceptionHandling()
                    .accessDeniedPage("/login?access=denied");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**");
    }
}
