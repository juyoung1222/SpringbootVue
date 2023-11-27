package com.care.bedu.community.reply.service.serviceimpl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.care.bedu.community.reply.dao.ReplyDAO;
import com.care.bedu.community.reply.service.ReplyService;
import com.care.bedu.community.reply.vo.ReplyVO;

@Service
public class ReplyServiceImpl implements ReplyService{
	
	@Autowired private ReplyDAO replyDAO;
	
	//댓글조회
	@Override
	public ArrayList<ReplyVO> getreply(ReplyVO replyVO) {
		if(replyVO.getQsNum() != null && replyVO.getQsNum() > 0) {
			return replyDAO.viewList3(replyVO);
		}else {
			return replyDAO.viewList(replyVO);
		}
	}

	//댓글쓰기
	@Override
	public int boardwrite(ReplyVO replyVO) {
		if(replyVO.getQsNum() != null && replyVO.getQsNum() > 0 && replyVO.getAnsNum()!= null && replyVO.getAnsNum()>0) {
			return replyDAO.viewWrite2(replyVO);
		}
		return replyDAO.viewWrite(replyVO);
	}

	//댓글 삭제
	@Override
	public int replydelete(ReplyVO replyVO) {
		if(replyVO.getCommNum() != null && replyVO.getCommNum() >0
				&& replyVO.getUserName().equals("ADMIN") && replyVO.getUserName() != null) {
			return replyDAO.replyadminDelete(replyVO);									
		}else if(replyVO.getReplyNum() != null && replyVO.getReplyNum()>0){
			return replyDAO.replyDelete(replyVO);
		}else if(replyVO.getAnsNum() != null && replyVO.getAnsNum() >0
				&& replyVO.getUserName().equals("ADMIN") && replyVO.getUserName() != null) {
			return replyDAO.replyadminalldelete(replyVO);
		}
		else {
			return 0;
		}
		
	}

	//댓글 수정
	@Override
	public int replyupdate(ReplyVO replyVO) {
		return replyDAO.replyupdate(replyVO);
	}
	//1대1 문의사항 답글 조회
	@Override 
	public ArrayList<ReplyVO> getinquiry(ReplyVO replyVO){
		ArrayList<ReplyVO> comment = replyDAO.inquiryList(replyVO);
		return comment;
	}
	
	//1대1 문의사항 답글쓰기
	@Override
	public int inquirywrite(ReplyVO replyVO) {
		return replyDAO.inquiryInsert(replyVO);
	}
	
	//1대1 문의사항 답글 삭제
	@Override
	public int inquirydelete(int inquiryNum) {
		System.out.println(inquiryNum);
		return replyDAO.inquiryDelete(inquiryNum);
	}

	//댓글 개수
	@Override
	public int replyTotal(ReplyVO replyVO) {
		if(replyVO.getCommNum() != null && replyVO.getCommNum() > 0) {	
			return replyDAO.replycommTotal(replyVO);	
		}
		return replyDAO.replyTotal(replyVO);
	}

}
