package edu.northeastern.csye.tms.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers("/createTask").hasRole("ADMIN");
            auth.requestMatchers("/updateTask").hasRole("ADMIN");
            auth.requestMatchers("/deleteTask").hasRole("ADMIN");
            auth.requestMatchers("/addTasks").hasRole("ADMIN");
            auth.requestMatchers("/editTasks").hasRole("ADMIN");
            auth.requestMatchers("/landingPage").hasAnyRole("ADMIN", "USER");
            auth.requestMatchers("/addPulseToTask").hasAnyRole("ADMIN", "USER");
            auth.requestMatchers("/getTasks").hasAnyRole("ADMIN", "USER");
            auth.requestMatchers("/displayTask").hasAnyRole("ADMIN", "USER");
            auth.requestMatchers("/getUsers").hasAnyRole("ADMIN", "USER");
            auth.requestMatchers("/api/register").permitAll();
        });

//        http.formLogin(loginConfigurer ->{
//            loginConfigurer.loginPage("/login");
////            loginConfigurer.defaultSuccessUrl("/landingPage", true);
////            loginConfigurer.failureUrl("/login?error=true").permitAll();
//        });

//        http.logout(logoutConfigurer -> {
//            logoutConfigurer.logoutSuccessUrl("/login?signout=true");
//            logoutConfigurer.invalidateHttpSession(true);
//        });

        http.csrf(csrf -> {
            csrf.disable();
        });

        return http.build();
    }

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
                + "WHERE u.username=?");

        return jdbcUserDetailsManager;
    }
}