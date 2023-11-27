//작성자: 황용학


package com.care.bedu.inquiry.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.care.bedu.inquiry.vo.InquiryVO;

@Mapper
public interface InquiryDAO {
	
	//자주 묻는 질문 조회
	public List<InquiryVO> faqlist() throws SQLException;
	
	//자주 묻는 질문 검색
	public List<InquiryVO> faqsearch(String faqKeyword) throws SQLException;
	
	//리스트 조회
	public List<InquiryVO> inquirylist() throws SQLException;
	
	//검색 리스트 조회
	public List<InquiryVO> inquirysearch(String keyword) throws SQLException;
	
	//게시글 작성
	public int inquiryWriteSave(InquiryVO inquiryVO); 
	
	//게시글 상세 보기
	public InquiryVO inquiryone(Integer vocNum);	

	//게시글 삭제
	public int inquirydelete(Integer vocNum);
	
	//게시글 전체 개수 조회
	public int getTotal(); 
	
	//유저아이디조회
	public ArrayList<InquiryVO> getuserId(String userName);	

	
	//유저 닉네임 조회
	public List<InquiryVO> getuserName(String userId);	
	
	//댓글 갯수
	public int getReplyCnt(Integer vocNum);
}