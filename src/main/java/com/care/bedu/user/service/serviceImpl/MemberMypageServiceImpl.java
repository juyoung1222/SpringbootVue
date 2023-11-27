/* 작업자 우지원 */



package com.care.bedu.user.service.serviceImpl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.care.bedu.user.dao.MemberMypageDAO;
import com.care.bedu.user.service.MemberMypageService;
import com.care.bedu.user.vo.MemberMypageVO;


import jakarta.transaction.Transactional;


@Service
public class MemberMypageServiceImpl implements MemberMypageService {
    private final MemberMypageDAO memberMypageDao;

    @Autowired
    public MemberMypageServiceImpl(MemberMypageDAO memberMypageDao) {
    	this.memberMypageDao = memberMypageDao;
    }
    @Transactional
    
    /* 마이페이지 홈(유저아이디 받아오기) */
    @Override
    public List<MemberMypageVO> getMemberMypage(String userName) {
    	return memberMypageDao.getMemberMypage(userName);
    }
    
    /* 마이페이지 홈(전체보기 개수 추출) */
    @Override
	public int getLectureCount(String userName, String group, String keyword) {
		HashMap<String,Object> args = new HashMap<>();

		args.put("userName", userName);
		args.put("group",group);
		args.put("keyword", keyword);

		return memberMypageDao.getLectureCount(args);
	}
    
    /* 마이페이지 홈(전체보기 첫번째) */
	@Override
	public List<MemberMypageVO> lectureListInfoFirst(String userName, int numOfLecture) {
		
		return memberMypageDao.lectureListInfoFirst(userName, numOfLecture);
	}
	
	/* 마이페이지 홈(전체보기 클릭 시 화면이동) */
	@Override
	public List<MemberMypageVO> getMemberMypageAll(String userName, int startNo, int numOfPage,String order, String group, String keyword) {
		HashMap<String , Object> args = new HashMap<>();

		args.put("userName", userName);
		args.put("startNo", startNo);
		args.put("order", order);
		args.put("group", group);
		args.put("keyword", keyword);
		
		return memberMypageDao.getMemberMypageAll(args);
	}
	@Override
	public Map<String, Object> getRecentlyViewd(String userName) {
		return memberMypageDao.getRecentlyViewd(userName);
	}
}