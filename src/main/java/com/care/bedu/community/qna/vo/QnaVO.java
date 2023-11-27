package com.care.bedu.community.qna.vo;

import java.util.Date;

import lombok.Data;
import lombok.ToString;
//질문답변게시판 객체(qna)
@Data
@ToString
public class QnaVO {					
	
	//글번호
	private Integer qnaBdNum;	
	
	//제목
	private String title;	
	
	//내용
	private String content;	
	
	//작성자닉네임
	private String userName;	
	
	// 수정아이디
	private String regId;	
	
	//user회원테이블의 유저 아이디
	private String userId;	
	
	//회원글번호
	private String userNum; 
	
	//작성시간
	private Date qnaDate;	
	
	//게시판 시간
	private Date regDate;	
	
	//조회수
	private Integer qnaCnt;	
	
	//좋아요
	private Integer qnaLikeCnt;	
	
	//검색 키워드
	private String keyword;		
	
	//글시작번호
	private int page;	
	
	//글끝번호
	private int limit;	

}
