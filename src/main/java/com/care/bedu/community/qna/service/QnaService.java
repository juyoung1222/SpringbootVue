package com.care.bedu.community.qna.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.care.bedu.community.qna.vo.QnaVO;

public interface QnaService {
	
	//게시글 리스트 5개 조회
	public List<QnaVO> listProc(QnaVO qnaVO);
	
	//본인글 조회
	public List<QnaVO> namelist(String name, int page);
	
	//게시글 작성
	public int boardwrite(QnaVO qnaVO);						
	
	//게시글 상세보기
	public QnaVO viewone(int num, String userName, String regid);
	
	//수정페이지 글조회
	public QnaVO qnaEditDetail(int num);	
	
	//게시글 삭제
	public int viewdelete(QnaVO qnaVO);					
	//게시글 수정
	public int viewupdate(QnaVO qnaVO);	
	
	//게시글 전체 개수
	public int getTotal();
	
	//게시글 유저 아이디 조회
	public ArrayList<QnaVO> getUserId(String userName);
	
	//게시글 좋아요 1 증가
	public HashMap<String, Object> likeUp(int num, String userName, String regId, String likeyn);
	
	//게시글 좋아요 1 감소
	public int likeDown(int num, String userName, int likeBdNum);
	
}
