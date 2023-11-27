package com.care.bedu.community.reply.service;

import java.util.ArrayList;

import com.care.bedu.community.reply.vo.ReplyVO;

public interface ReplyService {
	
	//게시글 조회
	public ArrayList<ReplyVO> getreply(ReplyVO replyVO);
	//게시글 등록
	public int boardwrite(ReplyVO replyVO);
	//댓글 총개수
	public int replyTotal(ReplyVO replyVO);
	//댓글 삭제
	public int replydelete(ReplyVO replyVO);
	
	//댓글 수정
	public int replyupdate(ReplyVO replyVO);
	
	//1대1 답글 조회
	public ArrayList<ReplyVO> getinquiry(ReplyVO replayVO);
	//1대1 답글 등록
	public int inquirywrite(ReplyVO replyVO);
 	//1대1 답글 삭제
	public int inquirydelete(int inquiryNum);
}
