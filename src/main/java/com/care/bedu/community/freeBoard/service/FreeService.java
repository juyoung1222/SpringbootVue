package com.care.bedu.community.freeBoard.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.care.bedu.community.freeBoard.vo.FreeVO;

public interface FreeService {
	
	//게시글 조회
	public List<FreeVO> listProc(FreeVO freeVO);
	
	////본인글 조회
	public List<FreeVO> namelist(String name, int page);
	
	//게시글 등록
	public int boardwrite(FreeVO freeVO);							
	//게시글 상세보기
	public FreeVO viewone(int num, String userName);
	
	//수정페이지 글 상세보기
	public FreeVO editdetail(int num);
	
	//게시글 삭제
	public int viewdelete(int num);						
	//게시글 수정
	public int viewupdate(int comm_num, String title, String content);	
	
	//게시글 전체 개수
	public int getTotal();				
	//유저아이디 조회
	public ArrayList<FreeVO> getUserId(String userName);			
	
	//좋아요1증가
	public HashMap<String, Object> likeUp(int num, String email, String regId, String likeyn);	
	
	//게시글 좋아요 1 감소
	public int likeDown(int num, String userName, int likeBdNum);
	
}
