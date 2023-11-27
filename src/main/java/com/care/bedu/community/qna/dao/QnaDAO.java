package com.care.bedu.community.qna.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.care.bedu.community.qna.vo.QnaVO;

@Mapper
public interface QnaDAO {
	
	//게시글 전체 조회
	public List<QnaVO> viewlist(QnaVO qnaVO);
	
	//본인이 쓴글에 한해서 전체 조회
	public List<QnaVO> nameview(String userName, int page);
	
	//게시글 작성
	public int viewWrite(QnaVO qnaVO);						
	
	//게시글 상세보기
	public QnaVO viewone(int num);					
	
	//게시글 수정
	public int viewupdate(QnaVO qnaVO);					
	
	//게시글 삭제
	public int viewdelete(QnaVO qnaVO);
	
	//답변글 삭제
	public void viewansdelete(QnaVO qnaVO);
		
	//댓글 삭제
	public void viewreplydelete(QnaVO qnaVO);
	
	//게시글 키워드 검색
	public ArrayList<QnaVO> viewsearch(QnaVO qnaVO);		
	//게시글 전체 개수 조회
	public int getTotal();										
	
	//유저아이디조회
	public ArrayList<QnaVO> getuserId(String userName);			
	
	//질문글 조회수 증가
	public void qnaCntUp(int num);					
	
	//질문글 좋아요 1증가
	public int likeUp(int num);									
	
	//질문글 좋아요 1감소
	public int likeDown(int num);									
	
	//상세페이지 접근에 대한 기존에 있는 조회 데이터 비교
	public int likeName(int qsnum, String userid, String likeyn);	
}
