package com.jjbeto.microservice.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final String appUser;

    private final String appPassword;

    public SecurityConfig(@Value("${app.user}") String appUser,
                          @Value("${app.password}") String appPassword) throws Exception {
        this.appUser = appUser;
        this.appPassword = appPassword;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/actuator/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .defaultSuccessUrl("/")
                .and()
                .logout().logoutUrl("/logout").permitAll()
                .logoutSuccessUrl("/login");
    }

    @Bean
    public UserDetailsService userDetailsService() {
        final var inMemoryUser = User.builder()
                .username(appUser)
                .password("{noop}" + appPassword)
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(inMemoryUser);
    }

}
