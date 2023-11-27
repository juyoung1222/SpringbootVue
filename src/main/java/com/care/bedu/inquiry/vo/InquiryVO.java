//작성자: 황용학


package com.care.bedu.inquiry.vo;

import java.util.Date;

import lombok.Data;

@Data
public class InquiryVO {
	
	// 자주 묻는 질문 번호
	private Integer faqNum;
	
	// 자주 묻는 질문 제목
	private String faqTitle; 
	
	// 자주 묻는 질문 내용
	private String faqContent; 
	
	//자주 묻는 질문 키워드
	
	private String faqKeyword;
	
	// 글번호
	private Integer vocNum; 
	
	// 제목
	private String title; 
	
	// 내용
	private String content; 
	
	// .파일 왼쪽 부분
	private String fileName; 
	
	// .파일 우측 부분
	private String fileType; 
	
	// 유저 이름
	private String userName; 
	
	// 유저 아이디
	private String userId;
	
	// 문의 시간
	private Date regDate; 
	
	// 타 테이블에서 구분
	private String regId; 
	
	//검색 키워드
	private String keyword;		
	
	// 비밀번호
	private String password;
	
	//글시작번호0
	private int page;	
	
	//글끝번호
	private int limit;
	
	//답글 갯수
	private int replyCnt;
	
	private boolean isSecret;
	
    public boolean isSecret() {
        return isSecret;
    }

    public void setSecret(boolean secret) {
        isSecret = secret;
    }
}