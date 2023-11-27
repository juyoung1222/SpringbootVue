package com.care.bedu.community.ans.vo;

import java.util.Date;

import lombok.Data;
import lombok.ToString;
//답글
@Data
@ToString
public class AnsVO{						
	
	//글번호
	private Integer ansBdNum;
	
	//작성이름
	private String userName;
	
	//내용
	private String content;
	
	//작성날짜
	private Date ansDate;
	
	//질문글번호
	private int qsBdNum;
	
	//좋아요
	private Integer ansLikeCnt;
	
	//수정날짜
	private Date regDate;
	
	//작성아이디
	private String regId;
	
	private int replyTotal;

}
