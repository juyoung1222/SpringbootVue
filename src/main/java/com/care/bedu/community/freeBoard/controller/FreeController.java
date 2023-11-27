package com.care.bedu.community.freeBoard.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.care.bedu.community.freeBoard.service.FreeService;
import com.care.bedu.community.freeBoard.vo.FreeVO;

@RestController
@RequestMapping("/api")
public class FreeController {
	
	@Autowired
	private FreeService freeService;
	
	//게시글 조회
	@RequestMapping(value="/freBd/boardList", method= {RequestMethod.GET, RequestMethod.POST})   		
	public List<FreeVO> freeList(FreeVO freeVO){
		return freeService.listProc(freeVO);
	}
	
	//작성자게시글 조회
		@RequestMapping(value="/freBd/namelist", method= {RequestMethod.GET, RequestMethod.POST})   		
		public List<FreeVO> freename(int page, String userName){
			return freeService.namelist(userName, page);
		}
	
	//게시글 작성
	@RequestMapping(value="/freBd/write", method=RequestMethod.POST)				
	public int freeWrite(FreeVO freeVO){
		return freeService.boardwrite(freeVO);
	}
	
	//상세보기
	@RequestMapping(value="/freBd/detail", method=RequestMethod.GET)				
	public FreeVO freeDetail(int num, String userName){
		return freeService.viewone(num, userName);	
	}
	
	//게시글 수정
	@RequestMapping(value="/freBd/edit", method=RequestMethod.POST)					
	public int freeEdit(int comm_num, String title, String content){
		return freeService.viewupdate(comm_num, title, content);	
	}
	
	//수정페이지 글 조회
	@RequestMapping(value="/freBd/editdetail", method=RequestMethod.GET)				
	public FreeVO freeEditDetail(int num){
		return freeService.editdetail(num);	
	}
	
	//게시글 삭제
	@RequestMapping(value="/freBd/delete", method=RequestMethod.GET)				
	public int freeDelete(int num){
		return freeService.viewdelete(num);
	}
	
	//전체 게시글 개수 조회
	@RequestMapping("/freBd/total")													
	public int getTotal(){
		return freeService.getTotal();
	}
	
	
	//닉네임에 해당하는 아이디 조회
	@RequestMapping(value="/free/getUserId", method = RequestMethod.GET)													//게시글 전체 개수 조회
	 public ArrayList<FreeVO> getUserId(String userName){
	 	 return freeService.getUserId(userName);
	 }
	
	//게시글 좋아요 개수 1증가
	@RequestMapping(value="/free/likeUp", method = RequestMethod.GET)				
	 public HashMap<String, Object> freelikeUp(int num, String userName, String regId, String likeyn) throws Exception{
		 return freeService.likeUp(num, userName, regId, likeyn);
	 }
	 
	//게시글 좋아요 1감소
	 @RequestMapping(value="/free/likeDown", method = RequestMethod.GET)													//게시글 전체 개수 조회
	 public int freelikeDown(int num, String userName, int likebdnum){
		 return freeService.likeDown(num, userName, likebdnum);
	 }
	
}
