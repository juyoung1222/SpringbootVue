//작성자 이준원


package com.care.bedu.user.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
    // 비밀번호 암호화에 사용할 BCryptPasswordEncoder 빈 등록
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 보안 필터 체인 설정
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF(Cross-Site Request Forgery) 보안 기능 비활성화
    	http.csrf().disable()
            // 모든 요청에 대해 권한 검사를 수행
	    	.authorizeHttpRequests()
	    	.requestMatchers("/api/**").permitAll() // Allow unauthenticated access
	    	.anyRequest().authenticated() // Require authentication for all other requests
            .and()
            // CORS(Cross-Origin Resource Sharing) 설정을 활성화
            .cors().and()
            // 폼 로그인 기능 비활성화 (API 기반의 인증 방식을 사용하기 때문에 폼 로그인은 필요 없음)
            .formLogin().disable();
        
        // SecurityFilterChain 객체를 생성하여 반환
        return http.build();
    }
}
