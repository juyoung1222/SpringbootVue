package com.care.bedu.community.freeBoard.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.care.bedu.community.freeBoard.vo.FreeVO;

@Mapper
public interface FreeDAO {
	
	//게시글 기본 조회
	public List<FreeVO> viewlist(FreeVO freeVO);
	
	//게시글 작성
	public int viewWrite(FreeVO freeVO);
	//게시글 상세 보기
	public FreeVO viewone(int num);	
	
	//본인이 작성한글 전부 조회 
	public List<FreeVO> nameview(String userName, int page);
	
	//게시글 삭제
	public int viewdelete(int num);							
	//게시글 수정
	public int viewupdate(FreeVO freeVO);
	
	//게시글 전체 개수
	public int getTotal();									
	
	//게시글 키워드 검색 기능	
	public ArrayList<FreeVO> viewsearch(FreeVO freeVO);		
	
	//조회수 1증가	
	public int cntUp(int num);							
	//유저아이디조회
	public ArrayList<FreeVO> getuserId(String userName);		
	//좋아요 1증가
	public int freelikeUp(int num);						
	//좋아요 1감소
	public int freelikeDown(int num);							
}
