package dev.kevinyu.service.restful.security;

import dev.kevinyu.service.restful.common.RoleType;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // Admin Role
                .antMatchers(HttpMethod.PUT, "/api/v1/books/*/authors").hasRole(RoleType.Admin.getDescription())
                .antMatchers(HttpMethod.DELETE).hasRole(RoleType.Admin.getDescription())
                // Regular Role
                .antMatchers(HttpMethod.GET).hasAnyRole(RoleType.Admin.getDescription(),RoleType.User.getDescription())
                .antMatchers(HttpMethod.POST).hasAnyRole(RoleType.Admin.getDescription(),RoleType.User.getDescription())
                .antMatchers(HttpMethod.PUT).hasAnyRole(RoleType.Admin.getDescription(),RoleType.User.getDescription())
                .antMatchers(HttpMethod.PATCH).hasAnyRole(RoleType.Admin.getDescription(),RoleType.User.getDescription())
                .anyRequest()
                .authenticated()
                .and().headers().cacheControl();
    }
}
