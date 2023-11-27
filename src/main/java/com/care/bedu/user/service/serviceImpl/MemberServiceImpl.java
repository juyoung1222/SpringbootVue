//작성자 이준원


package com.care.bedu.user.service.serviceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.care.bedu.user.dao.MemberDAO;
import com.care.bedu.user.service.MemberService;
import com.care.bedu.user.vo.MemberVO;

import jakarta.transaction.Transactional;
//Spring의 Service 어노테이션을 사용하여, 이 클래스가 서비스 빈으로 등록되도록 합니다.
@Service
public class MemberServiceImpl implements MemberService {
    private final MemberDAO memberDao;
    // MemberDAO의 인스턴스를 주입받는 생성자입니다.
    @Autowired
    public MemberServiceImpl(MemberDAO memberDao) {
    	this.memberDao = memberDao;
    }
    // 회원 가입 기능을 구현한 메서드입니다.
    @Transactional
    @Override
    public void register(MemberVO memberVo) {
    	// 사용자가 입력한 비밀번호를 바이트 배열로 변환합니다.
    	byte[] password = memberVo.getPassword().getBytes();
    	// Base64 인코딩을 사용하여 비밀번호를 암호화합니다.
    	String encodePwd= Base64.getEncoder().encodeToString(password);
        memberVo.setPassword(encodePwd);	// 암호화된 비밀번호로 설정합니다.
        memberVo.setEmail(memberVo.getEmail()); // 사용자 입력 이메일 설정
        memberVo.setNickname(memberVo.getNickname()); // 사용자 입력 닉네임 설정
        memberVo.setRegDate(LocalDateTime.now()); 
        memberVo.setCls("USER"); // 사용자 Cls를 'USER'로 설정합니다.
        memberVo.setUrd(LocalDate.now()); 
        memberVo.setUdy("N"); // 탈퇴 여부를 'N'으로 설정합니다.
        memberDao.register(memberVo);
    }
    // 입력한 이메일이 중복되는지 확인하는 메서드입니다.
    public boolean isEmailDuplicate(String email) {
        int count = memberDao.countByEmail(email);
        return count > 0;
    }
    // 입력한 닉네임이 중복되는지 확인하는 메서드입니다.
    public boolean isNicknameDuplicate(String nickname) {
        int count = memberDao.countByNickname(nickname);
        return count > 0;
    }
    // 이메일에 해당하는 비밀번호를 조회하는 메서드입니다.
    public String getPasswordByEmail(String email) {
        return memberDao.getPasswordByEmail(email);
    }
    // 이메일에 해당하는 회원 정보를 조회하는 메서드입니다.
    public MemberVO getMemberByEmail(String email) {
        return memberDao.getMemberByEmail(email);
    }
    // 이메일에 해당하는 회원이 좋아요한 게시물 번호 리스트를 조회하는 메서드입니다.
    @Override
    public List<Integer> getLikedBoardNumbersByEmail(String email) {
        return memberDao.getLikedBoardNumbersByEmail(email);
    }
    // 이메일에 해당하는 회원이 좋아요한 질문답변 게시물 번호 리스트를 조회하는 메서드입니다.
    @Override
    public List<Integer> getLikedQSBoardNumbersByEmail(String email) {
        return memberDao.getLikedQSBoardNumbersByEmail(email);
    }
    // 해당 닉네임의 구독 정보 여부를 확인하는 메서드입니다.
	@Override
	public boolean getSubInfo(String nickname) {
		int count = memberDao.getSubInfo(nickname);
        return count > 0;
	}
	
	@Override
    public void passwordChange(String email, String password) {
        // Base64 인코딩을 사용하여 비밀번호를 암호화합니다.
        String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());
        memberDao.passwordChange(email, encodedPassword);
    }
	
	@Override
    public void withDraw(String email) {
        memberDao.withDraw(email);
    }
}