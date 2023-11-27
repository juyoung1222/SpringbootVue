/* 작업자 우지원 */



package com.care.bedu.membership.service.serviceImpl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.care.bedu.membership.dao.MembershipDAO;
import com.care.bedu.membership.service.MembershipService;
import com.care.bedu.membership.vo.MembershipVO;


@Service
public class MembershipServiceImpl implements MembershipService{

	@Autowired
	MembershipDAO dao;
	
	@Override
	public int getSubscribe(String nickname, String type) {
		
		MembershipVO vo = new MembershipVO(0, nickname, type, null, null);
		
		if(type.equals("month") || type.equals("year")) {
			return dao.getSub(vo);
		} else {
			return 0;
		}

		
		
	}

	@Override
	public HashMap<String, Object> getSubInfo(String nickname) {
		
		HashMap<String, Object> map = new HashMap<>();
		
		MembershipVO vo = new MembershipVO();
		
		vo = dao.getSubInfo(nickname);
		
		map.put("item", vo);
		
		return map;
	}
}
