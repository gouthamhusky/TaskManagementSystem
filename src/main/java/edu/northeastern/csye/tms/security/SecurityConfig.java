package edu.northeastern.csye.tms.security;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 This class is responsible for configuring the security for the web application wrt HTTP requests and authentication,
 to secure the web application and protect the application resources from unauthorized access.
 @author Goutham K
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     Configures HTTP requests and authentication for the web application.
     @param http HttpSecurity object used to configure HTTP requests and authentication.
     @return SecurityFilterChain object used to configure the security filter chain.
     @throws Exception Exception thrown if there is an error configuring the security.
     */
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers("/home").hasAnyRole("ADMIN", "USER");
            auth.requestMatchers("/tasks/view").hasRole("ADMIN");
            auth.requestMatchers("/tasks/user/view").hasAnyRole("ADMIN", "USER");
            auth.requestMatchers("/tasks/add/view").hasRole("ADMIN");
            auth.requestMatchers("/tasks/update").hasRole("ADMIN");
            auth.requestMatchers("/tasks/add").hasRole("ADMIN");
            auth.requestMatchers("/tasks/delete").hasRole("ADMIN");
            auth.requestMatchers("/users/update/view").permitAll();
            auth.requestMatchers("/users/update").permitAll();
            auth.requestMatchers("/pulses/update/view").hasAnyRole("ADMIN", "USER");
            auth.requestMatchers("/pulses/update").hasAnyRole("ADMIN", "USER");
            auth.requestMatchers("/pulses/add/view").hasAnyRole("ADMIN", "USER");
            auth.requestMatchers("/pulses/add").hasAnyRole("ADMIN", "USER");

            auth.requestMatchers("/pulses/delete").permitAll();
            auth.requestMatchers("/pulses/update/view").permitAll();
            auth.requestMatchers("/tasks/update/view").permitAll();
            auth.requestMatchers("/v3/api-docs").permitAll();
            auth.requestMatchers("/swagger-ui.html").permitAll();
            auth.requestMatchers("/swagger-ui/*").permitAll();
            auth.requestMatchers("/v3/api-docs/swagger-config").permitAll();
            auth.requestMatchers("/users/save").permitAll();
            auth.requestMatchers("/actuator/**").permitAll();
            auth.requestMatchers("/js/*").permitAll();
            auth.requestMatchers("/css/*").permitAll();
            auth.requestMatchers("/username").permitAll();
        });

        http.formLogin(loginConfigurer ->{
            loginConfigurer.loginPage("/login").permitAll();
            loginConfigurer.defaultSuccessUrl("/home", true);
            loginConfigurer.failureUrl("/login?error=true");
        });

        http.logout(logoutConfigurer -> {
            logoutConfigurer.logoutUrl("/logout").permitAll();
            logoutConfigurer.logoutSuccessUrl("/login").permitAll();
            logoutConfigurer.invalidateHttpSession(true);
            logoutConfigurer.deleteCookies("JSESSIONID");
        });

        return http.build();
    }

    /**
     Returns an instance of {@link UserDetailsManager} that is responsible for retrieving user details and authorities
     from the database using JDBC. The JDBC queries for retrieving user details and authorities are configured in this method.
     @param dataSource the data source used to connect to the database
     @return an instance of {@link UserDetailsManager} configured to retrieve user details and authorities from the database
     */
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){

        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        jdbcUserDetailsManager.setUsersByUsernameQuery(
                "SELECT username, password, enabled FROM users WHERE username=?"
        );

        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
            "SELECT u.username, r.role_name\n"
                + "FROM users u\n"
                + "JOIN user_role ur ON u.user_id = ur.user_id\n"
                + "JOIN roles r ON r.role_id = ur.role_id\n"
                + "WHERE u.username=?"
        );

        return jdbcUserDetailsManager;
    }
}