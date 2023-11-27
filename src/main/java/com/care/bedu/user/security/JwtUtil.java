//작성자 이준원


package com.care.bedu.user.security;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtil {

    // application.properties 파일에서 jwt.secret 값 가져오기
    @Value("${jwt.secret}")
    String secret;

    // JWT를 생성하는 메소드
    public String createToken(String email, String nickname, int usernum, String cls, String udy,List<Integer> cbnumList, List<Integer> qsbnumList, boolean subInfo) {
        // HMAC256 알고리즘을 사용하여 JWT 생성에 필요한 암호화 키를 생성
        Algorithm algorithm = Algorithm.HMAC256(secret);
        // JWT 생성
        return JWT.create()
                .withIssuer("front") // 토큰 발행자를 설정 (임의의 문자열로 지정)
                .withClaim("usernum", usernum) // 사용자 번호 정보를 토큰에 포함
                .withClaim("email", email) // 이메일 정보를 토큰에 포함
                .withClaim("nickname", nickname) // 닉네임 정보를 토큰에 포함
                .withClaim("cls", cls) // cls 정보를 토큰에 포함
                .withClaim("udy", udy)
                .withClaim("cbnumList", cbnumList) // 좋아요한 게시물 번호 목록 정보를 토큰에 포함
                .withClaim("qsbnumList", qsbnumList) // 좋아요한 퀴즈 게시물 번호 목록 정보를 토큰에 포함
                .withClaim("subInfo", subInfo) // 구독 정보를 토큰에 포함
                .withIssuedAt(new Date()) // 토큰 발급 시간 설정
                .sign(algorithm); // 토큰에 서명하여 반환
    }

    // JWT를 디코딩하여 정보를 추출하는 메소드
    public DecodedJWT decodeToken(String token) {
        try {
            // HMAC256 알고리즘을 사용하여 JWT 검증에 필요한 암호화 키를 생성
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // JWT 검증을 위해 JWTVerifier 생성
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("front") // 토큰 발행자를 설정 (임의의 문자열로 지정)
                    .build();
            // 토큰 검증 및 디코딩하여 DecodedJWT 객체 반환
            return verifier.verify(token);
        } catch (JWTVerificationException e) {
            // JWT 검증 예외 처리
            log.error("JWTVerificationException: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            // JWT 문자열이 비어있을 경우 예외 처리
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return null;
    }
}