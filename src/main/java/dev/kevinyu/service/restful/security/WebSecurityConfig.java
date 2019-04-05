package dev.kevinyu.service.restful.security;

import dev.kevinyu.service.restful.common.RoleType;
import dev.kevinyu.service.restful.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsServiceImpl _userDetailsService;

    private BCryptPasswordEncoder _bCryptPasswordEncoder;

    private final RestAuthenticationEntryPoint _restAuthEntryPoint;

    @Autowired
    public WebSecurityConfig(RestAuthenticationEntryPoint restAuthenticationEntryPoint
            ,UserDetailsServiceImpl userDetailsService
            ,BCryptPasswordEncoder bCryptPasswordEncoder){
        this._restAuthEntryPoint = restAuthenticationEntryPoint;
        this._bCryptPasswordEncoder = bCryptPasswordEncoder;
        this._userDetailsService = userDetailsService;
    }

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(_userDetailsService).passwordEncoder(_bCryptPasswordEncoder);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(_userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // Admin Role
                .antMatchers(HttpMethod.POST, "/api/v1/books/*/authors").hasRole(RoleType.Admin.getName())
                .antMatchers(HttpMethod.DELETE, "/api/v1/books/*/authors").hasRole(RoleType.Admin.getName())
                .antMatchers(HttpMethod.POST, "/api/v1/authors/*/books").hasRole(RoleType.Admin.getName())
                .antMatchers(HttpMethod.DELETE, "/api/v1/authors/*/books").hasRole(RoleType.Admin.getName())
                // Regular Role
                .antMatchers(HttpMethod.GET).hasAnyRole(RoleType.Admin.getName(),RoleType.User.getName())
                .antMatchers(HttpMethod.POST).hasAnyRole(RoleType.Admin.getName(),RoleType.User.getName())
                .antMatchers(HttpMethod.PUT).hasAnyRole(RoleType.Admin.getName(),RoleType.User.getName())
                .antMatchers(HttpMethod.PATCH).hasAnyRole(RoleType.Admin.getName(),RoleType.User.getName())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
                .authenticationEntryPoint(_restAuthEntryPoint);
    }
}
