//작성자 이준원


package com.care.bedu.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.care.bedu.user.vo.AgreeVO;

@Mapper
public interface AgreeDAO {
	// 이용약관의 번호로 이용약관 리스트 불러오기
	List<AgreeVO> getAgreementById(Long id);
}
