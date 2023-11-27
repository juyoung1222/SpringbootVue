package com.care.bedu.community.ans.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.care.bedu.community.ans.service.AnsService;
import com.care.bedu.community.ans.vo.AnsVO;

@RestController
@RequestMapping("/api")
public class AnsController {
	
	@Autowired
	private AnsService ansService;
	
	//답변게시글 조회
	@RequestMapping(value="/ans/getans", method= {RequestMethod.GET, RequestMethod.POST})
	public ArrayList<AnsVO> getReply(AnsVO ansVO){
		return ansService.getlist(ansVO);
	}
	
	//답변게시글 작성
	@RequestMapping(value="/ans/write", method=RequestMethod.POST)
	public int ansWrite(int qsBdNum, String userName, String content, String regId){
		AnsVO ansVO = new AnsVO();
		ansVO.setQsBdNum(qsBdNum);
		ansVO.setUserName(userName);
		ansVO.setContent(content);
		ansVO.setRegId(regId);
		return ansService.boardwrite(ansVO);
	}
	
	//답변글 총개수
	@RequestMapping(value="/ans/ansTotal", method=RequestMethod.GET)													//게시글 전체 개수 조회
	public int ansTotal(int qnaNum){
	 	return ansService.ansTotal(qnaNum);
	}
	
	//답변글 총개수
	@RequestMapping(value="/ans/ansedit", method=RequestMethod.GET)													//게시글 전체 개수 조회
	public int ansedit(AnsVO ansVO){
		 return ansService.boardedit(ansVO);
	}
	
	//답변삭제
	@RequestMapping(value="/ans/ansdelete", method=RequestMethod.GET)													//게시글 전체 개수 조회
	public int ansDelete(AnsVO ansVO) {
		return ansService.ansDelete(ansVO);
		
	}

}
