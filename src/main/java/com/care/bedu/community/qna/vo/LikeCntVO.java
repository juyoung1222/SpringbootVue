package com.care.bedu.community.qna.vo;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LikeCntVO {
	
	//글번호
	private int likeNum;
	// 타 테이블에서 구분
	private String regId;
	//작성자닉네임
	private String userName;
	
	//질문게시글 번호
	private int qsBdNum;
	//자유게시글번호
	private int commBdNum;
	//게시판 시간
	private Date regDate;
	
	//좋아요클릭데이터
	private String likeyn;	
	
	
}
