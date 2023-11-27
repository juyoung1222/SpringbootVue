//작성자 이준원


package com.care.bedu.user.controller;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.care.bedu.user.security.JwtUtil;
import com.care.bedu.user.service.MemberService;
import com.care.bedu.user.vo.MemberVO;

@RestController
@RequestMapping("/api")
public class MemberController {
    
    private final MemberService memberService;
    private final JwtUtil jwtUtil;
    
    @Autowired
    public MemberController(MemberService memberService, JwtUtil jwtUtil) {
    	this.memberService = memberService;
		this.jwtUtil = jwtUtil;
    }

	// 회원 가입 요청
    @PostMapping("/register")
    public ResponseEntity<String> register(MemberVO memberVo) {
        memberService.register(memberVo);

        return new ResponseEntity<>("회원가입이 완료되었습니다.", HttpStatus.CREATED);
    }
	
	// 이메일 중복 체크
    @GetMapping("/register/email/{email}")
    public ResponseEntity<Boolean> checkEmailDuplicate(@PathVariable String email) {
        boolean isDuplicate = memberService.isEmailDuplicate(email);
        return ResponseEntity.ok(isDuplicate);
    }

    // 닉네임 중복 체크
    @GetMapping("/register/nickname/{nickname}")
    public ResponseEntity<Boolean> checkNicknameDuplicate(@PathVariable String nickname) {
        boolean isDuplicate = memberService.isNicknameDuplicate(nickname);
        return ResponseEntity.ok(isDuplicate);
    }
    
    // 로그인 요청
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> paramMap) {

        String email = paramMap.get("email");
        String password = paramMap.get("password");
    	// 이메일을 통해 사용자 정보 조회
        MemberVO loginUser = memberService.getMemberByEmail(email);
        
        if (loginUser != null) {
	        // 사용자의 비밀번호를 Base64로 인코딩되어 저장된 값으로 조회
	        String encodedPassword = memberService.getPasswordByEmail(email);
	        
	        if (encodedPassword != null) {
		        // Base64로 인코딩된 비밀번호를 디코딩하여 원래 비밀번호 값 복구
		        byte[] decodedBytes = Base64.getDecoder().decode(encodedPassword);
		        String decodedPassword = new String(decodedBytes);
		            
		        if (decodedPassword.equals(password)) {
		        	// 로그인 성공 시, 사용자가 좋아요를 누른 게시물 목록과 구독 정보를 조회
		            List<Integer> cbnumList = memberService.getLikedBoardNumbersByEmail(email);
		            List<Integer> qsbnumList = memberService.getLikedQSBoardNumbersByEmail(email);
		            boolean subInfo = memberService.getSubInfo(loginUser.getNickname());
		            // JWT를 사용하여 사용자의 정보를 암호화하여 토큰 생성
		            String accessToken = jwtUtil.createToken(loginUser.getEmail(), loginUser.getNickname(), loginUser.getUsernum(), loginUser.getCls(), loginUser.getUdy(), cbnumList, qsbnumList, subInfo);
		            // 로그인 성공 응답에 사용자 정보와 토큰을 담아서 반환
		            Map<String, Object> result = new HashMap<>();
		            result.put("user_token", accessToken);
		            
		            return ResponseEntity.ok(result);
		        }
	        }
        } else {
            // 로그인 실패 시, 사용자 정보가 없음을 반환
            Map<String, Object> error = new HashMap<>();
            error.put("message", "User not found");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
        // 로그인 실패 시, 인증 오류 응답 반환    
        Map<String, Object> error = new HashMap<>();
        error.put("message", "Invalid email or password");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }
    
    @PostMapping("/passwordChk")
    public ResponseEntity<Map<String, Object>> checkPassword(@RequestBody Map<String, String> paramMap) {
        String email = paramMap.get("email");
        String submittedPassword = paramMap.get("password");

        MemberVO user = memberService.getMemberByEmail(email);
        Map<String, Object> response = new HashMap<>();

        if (user != null) {
            String encodedPassword = memberService.getPasswordByEmail(email);

            if (encodedPassword != null) {
                byte[] decodedBytes = Base64.getDecoder().decode(encodedPassword);
                String decodedPassword = new String(decodedBytes);

                if (decodedPassword.equals(submittedPassword)) {
                    response.put("success", true);
                    response.put("message", "비밀번호 일치");
                } else {
                    response.put("success", false);
                    response.put("message", "비밀번호 불일치");
                }
            }
        } else {
            response.put("success", false);
            response.put("message", "사용자 정보를 찾을 수 없음");
        }

        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/passwordChange")
    public ResponseEntity<String> passwordChange(@RequestBody MemberVO memberVo) {
        try {
            memberService.passwordChange(memberVo.getEmail(), memberVo.getPassword());
            return new ResponseEntity<>("비밀번호 변경이 완료되었습니다.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("비밀번호 변경 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/withDraw/{email}")
    public ResponseEntity<String> withDraw(@PathVariable String email) {
        try {
            memberService.withDraw(email);
            return new ResponseEntity<>("회원탈퇴가 완료되었습니다.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("회원탈퇴 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
