package com.care.bedu.community.qna.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.care.bedu.community.qna.service.QnaService;
import com.care.bedu.community.qna.vo.QnaVO;

@RestController
@RequestMapping("/api")
public class QnaController {
	
	 @Autowired
	 private QnaService qnaService;
	
	 //질문게시글 조회
	 @RequestMapping(value="/qna/qnaList", method= {RequestMethod.GET, RequestMethod.POST})    //게시글 조회
	 public List<QnaVO> qnaList(QnaVO qnaVO){
		 return qnaService.listProc(qnaVO);
	 }
	 
	//질문게시글 작성자 이름으로 조회
		 @RequestMapping(value="/qna/nameList", method= {RequestMethod.GET, RequestMethod.POST})    //작성자글 조회
		 public List<QnaVO> nameList(String userName, int page){
			 return qnaService.namelist(userName, page);
		 }
	
	//게시글 작성
	 @RequestMapping(value="/qna/qnaWrite", method=RequestMethod.POST)				
	 public int qnaWrite(QnaVO qnaVO){
		 return qnaService.boardwrite(qnaVO);
	 }
	
	//게시글 상세보기
	 @RequestMapping(value="/qna/qnaDetail", method=RequestMethod.GET)
	 public QnaVO qnaDetail(int num, String userName, String regid){
	 	return qnaService.viewone(num, userName, regid);
	 }
	 
	//수정페이지 글 조회
	@RequestMapping(value="/qna/editdetail", method=RequestMethod.GET)				
	public QnaVO qnaEditDetail(int num){
		return qnaService.qnaEditDetail(num);	
	}
	
	//게시글 수정
	 @RequestMapping(value="/qna/qnaEdit", method=RequestMethod.POST)				
	 public int qnaEdit(String title, String content, int qnaBdNum){
		QnaVO qnaVO = new QnaVO();
		qnaVO.setContent(content);
		qnaVO.setQnaBdNum(qnaBdNum);
		qnaVO.setTitle(title);
	 	int result = qnaService.viewupdate(qnaVO);
	 	return result> 0? 1:0;	
	 }
	
	//게시글 삭제
	 @RequestMapping(value="/qna/qnaDelete", method=RequestMethod.GET)				
	 public int qnaDelete(QnaVO qnaVO){
	 	return qnaService.viewdelete(qnaVO);
	 }

	//게시글 전체 개수 조회
	 @RequestMapping("/qna/total")									
	 public int getTotal(){
	 	return qnaService.getTotal();
	 }
	 
	//현제 로그인된 닉네임에 해당하는 userid조회
	 @RequestMapping(value="/qna/getUserId", method = RequestMethod.GET)													//게시글 전체 개수 조회
	 public ArrayList<QnaVO> getUserName(String userName){
		 return qnaService.getUserId(userName);
	 }
	
	//게시글 좋아요 개수 1증가
	 @RequestMapping(value="/qna/likeUp", method = RequestMethod.GET)				
	 public HashMap<String, Object> likeUp(int num, String userName, String regId, String likeyn){
		 return qnaService.likeUp(num, userName, regId, likeyn);
	 }
	 
	 //게시글 좋아요 1감소
	 @RequestMapping(value="/qna/likeDown", method = RequestMethod.GET)													//게시글 전체 개수 조회
	 public int likeDown(int num, String userName, int likebdnum){
		 return qnaService.likeDown(num, userName, likebdnum);
	 }

}
