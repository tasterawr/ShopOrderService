package org.loktevik.netcracker.security;

import org.loktevik.netcracker.filter.AuthorizationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/orders").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/orders/**", "/orders/customer/**").hasAnyAuthority("USER");
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/orders").hasAnyAuthority("USER");
        http.authorizeRequests().antMatchers(HttpMethod.PUT,"/orders/**").hasAnyAuthority("USER", "ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/orders/**").hasAnyAuthority("USER", "ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.PATCH,"/orders/**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilterBefore(new AuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}