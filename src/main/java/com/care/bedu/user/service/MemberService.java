//작성자 이준원


package com.care.bedu.user.service;

import java.util.List;

import com.care.bedu.user.vo.MemberVO;

public interface MemberService {
	// 회원가입 service
	void register(MemberVO memberVo);
	// 이메일 중복체크 service
    boolean isEmailDuplicate(String email);
    // 닉네임 중복체크 service
    boolean isNicknameDuplicate(String nickname);
    // 이메일로 비밀번호 불러오는 service
    String getPasswordByEmail(String email);
    // 이메일로 유저정보 불러오는 service
    MemberVO getMemberByEmail(String email);
    // 유저가 좋아요를 누른 자유게시판 번호 service
    List<Integer> getLikedBoardNumbersByEmail(String email);
    // 유저가 좋아요를 누른 질문답변게시판 번호 service
    List<Integer> getLikedQSBoardNumbersByEmail(String email);
    /* 구독 정보 조회 */
    boolean getSubInfo(String nickname);
	// 비밀번호 변경 service
	void passwordChange(String email, String password);
	
	void withDraw(String email);
}
