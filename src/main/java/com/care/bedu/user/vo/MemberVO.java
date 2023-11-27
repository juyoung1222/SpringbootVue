//작성자 이준원


package com.care.bedu.user.vo;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class MemberVO {
	// 유저 번호
	private int usernum;
	// 유저 이메일
	private String email;
	// 유저 비밀전호
	private String password;
	// 유저 이름
	private String nickname;
	// 생성날짜
	private LocalDateTime regDate;
	// 계정권한
	private String cls;
	// 생성날짜 자새히 
	private LocalDate urd;
	// 탈퇴 여부
	private String udy;
	// 좋아요 누른 자유게시판 번호
	private int cbnum;
	// 좋아요 누른 질문답변게시판 번호	
	private int qsbnum;
	
}
