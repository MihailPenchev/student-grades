package com.ltp.gradesubmission.security;

import com.ltp.gradesubmission.security.filter.AuthenticationFilter;
import com.ltp.gradesubmission.security.filter.GlobalExceptionHandlingFilter;
import com.ltp.gradesubmission.security.filter.JWTAuthorizationFilter;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@AllArgsConstructor
public class SecurityConfig {
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;

    // basic configuration
    @Bean
    @ConditionalOnProperty(name="authtype", havingValue = "basic")
    public UserDetailsService basicUserService() {
        UserDetails admin = User.builder().username("admin").password(passwordEncoder.encode("admin-pass")).roles("ADMIN").build();
        UserDetails user = User.builder().username("user").password(passwordEncoder.encode("user-pass")).roles("USER").build();
        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    @ConditionalOnProperty(name="authtype", havingValue = "basic")
    public SecurityFilterChain basicFilterChain(HttpSecurity http) throws Exception{
         http.csrf().disable()
                 .authorizeRequests()
                 .antMatchers(HttpMethod.DELETE).hasRole("ADMIN")
                 .antMatchers(HttpMethod.POST).hasAnyRole("ADMIN", "USER")
                 .antMatchers(HttpMethod.GET).permitAll()
                 .anyRequest().authenticated()
                 .and()
                 .httpBasic()
                 .and()
                 .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }

    // jwt configuration
    @Bean
    @ConditionalOnProperty(name="authtype", havingValue = "jwt")
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                //.headers().frameOptions().disable().and() // only for h2
                .csrf().disable()
                .authorizeRequests()
                //.antMatchers("/h2/**").permitAll() // only for h2
                .antMatchers(HttpMethod.POST, SecurityConstants.REGISTER_PATH).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new GlobalExceptionHandlingFilter(), AuthenticationFilter.class)
                .addFilter(new AuthenticationFilter(authenticationManager, "/authenticate"))
                .addFilterAfter(new JWTAuthorizationFilter(), AuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }
}
