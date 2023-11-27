/* 작업자 우지원 */



package com.care.bedu.user.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestParam;

import com.care.bedu.user.vo.MemberMypageVO;


@Mapper
public interface MemberMypageDAO {
	
	/* 마이페이지 홈(유저아이디 받아오기) */
	public List<MemberMypageVO> getMemberMypage(String userName);
	
	/* 마이페이지 홈(전체보기 개수 추출) */
	public int getLectureCount(Map<String,Object> args);
	
	/* 마이페이지 홈(전체보기 첫번째) */
	public List<MemberMypageVO> lectureListInfoFirst(String userName, @RequestParam("numOfLecture") int numOfLecture);
	
	/* 마이페이지 홈(전체보기 클릭 시 화면이동) */
	public List<MemberMypageVO> getMemberMypageAll(Map<String,Object> args);

	/* 최근 학습 강의 조회 */
	public HashMap<String,Object> getRecentlyViewd(String userName);
}
