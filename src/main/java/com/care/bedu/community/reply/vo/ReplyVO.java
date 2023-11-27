package com.care.bedu.community.reply.vo;

import java.util.Date;

import lombok.Data;
import lombok.ToString;
//댓글
@Data
@ToString
public class ReplyVO{					
	
	//글번호
	private Integer replyNum;			
	
	//작성자
	private String userName;			
	
	//내용
	private String content;			
	
	//작성날짜
	private Date replyDate;			
	
	//수정날짜
	private Date regDate;			
	
	//자유글번호
	private Integer commNum;		
	
	//질문글번호
	private Integer qsNum;				
	
	//답변글번호
	private Integer ansNum;			
	
	//강의후기글번호
	private Integer rwNum;				
	
	//등록아이디
	private String regId;	
	
	//1대1문의글번호
	private Integer vocNum;

}