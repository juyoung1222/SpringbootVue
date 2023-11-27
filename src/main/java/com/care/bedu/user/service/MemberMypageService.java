/* 작업자 우지원 */



package com.care.bedu.user.service;

import java.util.List;
import java.util.Map;

import com.care.bedu.user.vo.MemberMypageVO;


public interface MemberMypageService {
	
	/* 마이페이지 홈(유저아이디 받아오기) */
	public List<MemberMypageVO> getMemberMypage(String userName);
	
	/* 마이페이지 홈(전체보기 개수 추출) */
	public int getLectureCount(String userName, String group, String keyword);
	
	/* 마이페이지 홈(전체보기 첫번째) */
	public List<MemberMypageVO> lectureListInfoFirst(String userName, int numOfLecture);
	
	/* 마이페이지 홈(전체보기 클릭 시 화면이동) */
	public List<MemberMypageVO> getMemberMypageAll(String userName, int startNo, int numOfPage, String order, String group, String keyword);

	/* 최근 학습 강의 조회 */
	public Map<String,Object> getRecentlyViewd(String userName);
}
