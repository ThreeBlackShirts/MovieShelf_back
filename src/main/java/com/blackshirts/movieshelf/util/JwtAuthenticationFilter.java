package com.blackshirts.movieshelf.util;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    // Request로 들어오는 Jwt Token의 유효성을 검증하는 filter를 filterChain에 등록합니다.
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        // header에서 JWT를 받아온다.
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) servletRequest);

        // 유효성 확인
        if (token != null && jwtTokenProvider.validateToken(token)) {
            // 토큰으로부터 유저 정보 받아옴
            Authentication auth = jwtTokenProvider.getAuthentication(token);    // 인증 객체 생성
            SecurityContextHolder.getContext().setAuthentication(auth); // SecurityContext 에 Authentication 객체를 저장
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}