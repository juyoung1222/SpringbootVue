package com.care.bedu.community.reply.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.care.bedu.community.reply.service.ReplyService;
import com.care.bedu.community.reply.vo.ReplyVO;

@RestController
@RequestMapping("/api")
public class ReplyController {
	
	@Autowired
	private ReplyService replyService;
	
	//댓글 조회
	@RequestMapping(value="/reply/getreply", method= {RequestMethod.GET, RequestMethod.POST})   		
	public ArrayList<ReplyVO> getReply(ReplyVO replyVO){
		return replyService.getreply(replyVO);
	}
	
	//댓글 작성
	@RequestMapping(value="/reply/write", method=RequestMethod.POST)				
	public int replyWrite(ReplyVO replyVO){
		return replyService.boardwrite(replyVO);
	}
	
	//댓글 총 개수
	@RequestMapping(value="/reply/replyTotal", method=RequestMethod.GET)													//게시글 전체 개수 조회
	public int replyTotal(ReplyVO replyVO){
	 	return replyService.replyTotal(replyVO);
	}
	
	//댓글 삭제
	@RequestMapping(value="/reply/replydelete", method=RequestMethod.GET)													//게시글 전체 개수 조회
	public int replyDelete(ReplyVO replyVO) {
		return replyService.replydelete(replyVO);
		
	}

	
	//댓글 수정
	 @RequestMapping(value="/reply/replyEdit", method=RequestMethod.POST)				
	 public int replyEdit(int replyNum, String content){
		 ReplyVO replyVO = new ReplyVO();
		 replyVO.setReplyNum(replyNum);
		 replyVO.setContent(content);
	 	 return replyService.replyupdate(replyVO);	
	 }
	 
		//1대1 문의사항 조회
		@RequestMapping(value="/reply/getinquiry", method= {RequestMethod.GET, RequestMethod.POST})
		public ArrayList<ReplyVO> getInquiry(ReplyVO replyVO){
			return replyService.getinquiry(replyVO);	
		}
		
		//1대1 문의사항 답글 등록
		@RequestMapping(value="/reply/inquiryWrite", method=RequestMethod.POST)
		public int inquiryWrite(Integer vocNum, String userName, String content, String regId) {
			
			ReplyVO replyVO = new ReplyVO();
			replyVO.setVocNum(vocNum);
			replyVO.setUserName(userName);
			replyVO.setContent(content);
			replyVO.setRegId(regId);
			
			System.out.println(replyVO);
			return replyService.inquirywrite(replyVO);
		}
		
//		//1대1 문의사항 답글 삭제
		@RequestMapping(value="/reply/inquirydelete", method=RequestMethod.GET)
		public int inquiryDelete(int replyNum) throws Exception{
			return replyService.inquirydelete(replyNum);
		}
}
