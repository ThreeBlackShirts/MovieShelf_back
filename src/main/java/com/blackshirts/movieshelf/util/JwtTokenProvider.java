package com.blackshirts.movieshelf.util;

import com.blackshirts.movieshelf.service.UserDetailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;


@Component
public class JwtTokenProvider {
    // JWT 작업 클래스
    // 토큰 생성, 토큰에서 (유저)정보 추출, 토큰 유효성 확인

    private static final Logger log = LoggerFactory.getLogger(JwtTokenProvider.class);

    private String secretKey;

    private final UserDetailService userDetailService;

    // 토큰 유효시간 30분
    private long tokenValidTime = 30 * 60 * 1000L;

    // 객체 초기화, secretKey를 Base64로 인코딩한다.
    public JwtTokenProvider(@Value("${security.jwt.token.secret-key}") String secretKey, UserDetailService userDetailService, @Value("${security.jwt.token.expire-length}") long tokenValidTime) {
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        this.tokenValidTime = tokenValidTime;
        this.userDetailService = userDetailService;
    }

    // 토큰 생성
    public String createToken(String userPk) { //email 받음
        Claims claims = Jwts.claims().setSubject(String.valueOf(userPk)); // JWT payload에 저장되는 정보 단위
        claims.put("userPk", userPk); // key/ value 쌍으로 저장

        Date now = new Date();
        Date validity = new Date(now.getTime() + tokenValidTime); // set Expire Time
//        log.info("now: {}", now);
//        log.info("validity: {}", validity);

        return Jwts.builder()
                .setClaims(claims)  // sub 설정 (정보 저장)
                .setIssuedAt(now)   // 토큰 발행 시간 정보
                .setExpiration(validity) // Set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey)
                // 사용할 암호화 알고리즘과 signature에 들어갈 secret값 세팅
                .compact();
    }

    // 토큰에서 값 추출
    public Long getSubject(String token) {
        return Long.valueOf(Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject());
    }

    // Jwt Token에서 UserPK 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(token).getBody().getSubject();
    }

    // 인증 성공시 SecurityContextHolder에 저장할 Authentication 객체 생성
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // Request의 Header에서 token 값을 가져오는 함수. "X-AUTH-TOKEN" : "TOKEN값'
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH-TOKEN");
    }

    // Token의 유효성 + 만료 기간 검사
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

}
