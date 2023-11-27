//작성자 이준원


package com.care.bedu.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.care.bedu.user.vo.MemberVO;

@Mapper
public interface MemberDAO {

	// 회원가입 dao
	void register(MemberVO memberVo);
    // 이메일 중복 체크 dao
    int countByEmail(String email);
    // 닉네임 중복 체크 dao
    int countByNickname(String nickname);
    // 이메일로 비밀번호 불러오는 dao
    String getPasswordByEmail(String email);
    // 이메일로 유저정보 불러오는 dao
    MemberVO getMemberByEmail(String email);
    // 유저가 좋아요를 누른 자유게시판 번호 dao
    List<Integer> getLikedBoardNumbersByEmail(String email);
    // 유저가 좋아요를 누른 질문답변게시판 번호 dao
    List<Integer> getLikedQSBoardNumbersByEmail(String email);
    /* 구독 정보 조회 */
    int getSubInfo(String nickname);
	// 비밀번호 변경 dao
    void passwordChange(String email, String password);
    
    void withDraw(String email);
}
