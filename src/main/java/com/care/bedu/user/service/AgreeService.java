//작성자 이준원


package com.care.bedu.user.service;

import java.util.List;

import com.care.bedu.user.vo.AgreeVO;

public interface AgreeService {
	// 이용약관의 번호로 이용약관 리스트 불러오기
	List<AgreeVO> getAgreementById(Long id);
}
