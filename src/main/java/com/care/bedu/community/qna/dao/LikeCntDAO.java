package com.care.bedu.community.qna.dao;

import org.apache.ibatis.annotations.Mapper;

import com.care.bedu.community.qna.vo.LikeCntVO;

@Mapper
public interface LikeCntDAO {
	
	//좋아요 클릭에대한 내용 저장
	public int likeCntSave(LikeCntVO likeCntVO);
	
	//좋아요 취소에 대한 내용 삭제
	public int likedel(int num);		
	//좋아요 글번호 조회
	public Integer getlikenum(int qnum, String userName, String likeyn);
	
	//상세페이지 접근에 대한 기존 조회데이터 비교
	public int qnaEqcnt(int qnanum, String userName);				
	
	//상세페이지 접근에 대한 조회데이터 저장
	public int cntqnaSave(LikeCntVO likeCntVO);							
}
