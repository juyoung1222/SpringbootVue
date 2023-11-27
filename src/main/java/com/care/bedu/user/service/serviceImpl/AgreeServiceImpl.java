//작성자 이준원


package com.care.bedu.user.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.care.bedu.user.dao.AgreeDAO;
import com.care.bedu.user.service.AgreeService;
import com.care.bedu.user.vo.AgreeVO;

@Service
public class AgreeServiceImpl implements AgreeService{
	private final AgreeDAO agreeDao;
	
	@Autowired
	public AgreeServiceImpl(AgreeDAO agreeDao) {
		this.agreeDao = agreeDao;
	}
	// 이용약관의 번호로 이용약관 리스트 불러오기
	@Override
	public List<AgreeVO> getAgreementById(Long id) {
	    return agreeDao.getAgreementById(id);
	}
	
}
