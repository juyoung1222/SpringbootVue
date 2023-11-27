package com.care.bedu.community.freeBoard.vo;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FreeVO {
	
	//글번호
	private Integer commNum;
	
	//글시작번호
	private int page;
	//글끝번호
	private int limit;
	// 타 테이블에서 구분
	private String regId;
	
	//작성자
	private String userName;
	
	//user회원테이블의 유저 아이디
	private String userId;
	
	//내용
	private String content;
	//회원글번호
	private String userNum;
	
	//작성시간
	private Date commDate;
	
	//수정 시간
	private Date regDate;
	
	//제목
	private String title;
	//조회수
	private Integer commCnt;
	
	//좋아요
	private Integer commLikeCnt;
	
	//검색 키워드
	private String keyword;
	
	private int replyTotal;

}
