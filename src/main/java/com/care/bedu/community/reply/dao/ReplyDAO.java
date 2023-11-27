package com.care.bedu.community.reply.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.care.bedu.community.reply.vo.ReplyVO;

@Mapper
public interface ReplyDAO {
	//자유댓글조회
	public ArrayList<ReplyVO> viewList(ReplyVO replyVO);
	//답변에 대한 댓글조회
	public ArrayList<ReplyVO> viewList3(ReplyVO replyVO);
	//댓글쓰기
	public int viewWrite(ReplyVO replyVO);
	//질문의 답변의 댓글쓰기
	public int viewWrite2(ReplyVO replyVO);
	//질문댓글개수
	public int replyTotal(ReplyVO replyVO);
	
	//자유댓글개수
	public int replycommTotal(ReplyVO replyVO);
	
	//댓글삭제
	public int replyDelete(ReplyVO replyVO);
	
	//자유댓글전체삭제(관리자)
	public int replyadminDelete(ReplyVO replyVO);
	
	//댓글수정
	public int replyupdate(ReplyVO replyVO);
	
	public int replyadminalldelete(ReplyVO replyVO);
	
	//1대1 문의사항 답급
	public int inquiryInsert(ReplyVO replyVO);
	
	//1대1 문의사항 답글 조회
	public ArrayList<ReplyVO> inquiryList(ReplyVO replyVO);
	
//	//1대1 문의사항 답글 삭제
	public int inquiryDelete(int inquiryNum);
}
