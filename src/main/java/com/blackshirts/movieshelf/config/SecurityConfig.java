package com.blackshirts.movieshelf.config;

import com.blackshirts.movieshelf.service.UserDetailService;
import com.blackshirts.movieshelf.service.UserService;
import com.blackshirts.movieshelf.util.JwtAuthenticationFilter;
import com.blackshirts.movieshelf.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    // 암호화에 필요한 PasswordEncoder 를 Bean.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override // Authorization
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .authorizeRequests() // 요청에 대한 사용권한 체크
//                .antMatchers("/**").permitAll()
                .antMatchers("/api/**").authenticated()
                .antMatchers("/exception/**", "/item/**", "/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**").permitAll() // For Swagger
                .antMatchers("/v1/register/**", "/v1/signup/**", "/v1/login/**", "/v1/logout/**").permitAll() // 로그인, 회원가입은 누구나 접근 가능
                .antMatchers("/google/**").permitAll()
                .antMatchers("/oauth/**").permitAll()
//                .antMatchers("/api/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
    }
}