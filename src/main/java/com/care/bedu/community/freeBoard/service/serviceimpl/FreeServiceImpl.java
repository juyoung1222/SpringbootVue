package com.care.bedu.community.freeBoard.service.serviceimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.care.bedu.community.freeBoard.dao.FreeDAO;
import com.care.bedu.community.freeBoard.dao.FreeLikeCntDAO;
import com.care.bedu.community.freeBoard.service.FreeService;
import com.care.bedu.community.freeBoard.vo.FreeVO;
import com.care.bedu.community.qna.vo.LikeCntVO;
import com.care.bedu.community.reply.dao.ReplyDAO;
import com.care.bedu.community.reply.vo.ReplyVO;

//자유게시판
@Service
public class FreeServiceImpl implements FreeService{
	
	@Autowired private FreeDAO freeDAO;
	
	@Autowired private ReplyDAO replyDAO;
	
	@Autowired private FreeLikeCntDAO freelikeCntDAO; 
	
	//자유게시글 조회
	@Override
	public List<FreeVO> listProc(FreeVO freeVO) {					
		freeVO.setLimit(10);
		freeVO.setPage((freeVO.getPage()-1) * freeVO.getLimit());							
		if(freeVO.getKeyword() != null) {				
			return freeDAO.viewsearch(freeVO);
		}
		else {
			List<FreeVO> list = freeDAO.viewlist(freeVO);
			ReplyVO replyVO = new ReplyVO();
			for(FreeVO free : list) {
				replyVO.setCommNum(free.getCommNum());
				int total = replyDAO.replycommTotal(replyVO);
				free.setReplyTotal(total);
			}
			return list;
		}
	}

	//자유글 작성
	@Override
	public int boardwrite(FreeVO freeVO) {

		freeVO.setCommCnt(0);
		freeVO.setCommLikeCnt(0);
		return freeDAO.viewWrite(freeVO);
	}

	
	//게시글 상세보기
	//조회수 증가
	@Override
	public FreeVO viewone(int commnum, String userName){									
		freeDAO.cntUp(commnum);
		return freeDAO.viewone(commnum);
		
	}

	//게시글 삭제
	@Override
	public int viewdelete(int num) {			
		return freeDAO.viewdelete(num);
	}

	//게시글 수정
	@Override
	public int viewupdate(int comm_num, String title, String content) {			
		FreeVO freeVO = new FreeVO();
		freeVO.setCommNum(comm_num);
		freeVO.setTitle(title);
		freeVO.setContent(content);
		return freeDAO.viewupdate(freeVO);
	}

	//최종 끝 페이지
	@Override
	public int getTotal() {										
		return freeDAO.getTotal();
	}


	//유저아이디 조회
	@Override
	public ArrayList<FreeVO> getUserId(String userName) {
		return freeDAO.getuserId(userName);
	}
	
	//좋아요 1증가
	@Override
	public HashMap<String, Object> likeUp(int commnum, String userName, String regId, String likeyns) {
		int number = freelikeCntDAO.getfreelikeName(commnum, userName, likeyns);
		
		Integer likeyn = freelikeCntDAO.getlikeFreenum(commnum, userName, likeyns);
		
		
		HashMap<String, Object> map = new HashMap<>();
		if(number == 0) {
			
			LikeCntVO likeCntVO = new LikeCntVO();
			likeCntVO.setUserName(userName);
			likeCntVO.setCommBdNum(commnum);
			likeCntVO.setRegId(regId);
			likeCntVO.setLikeyn(likeyns);
			Integer result = freelikeCntDAO.likeCntFreeSave(likeCntVO);
			
			if(result == 1) {
				Integer getnum = freeDAO.freelikeUp(commnum);
				
				map.put("likenum", likeyn);
				map.put("result", getnum);
				return map;
			}else {
		
				map.put("likenum", likeyn);
				map.put("result", result);
				return map;
			}
		}else {
			if(likeyn != null) {
				map.put("likenum", likeyn);
				map.put("result", 0);
				return map;
			}
			return null;
		}
	}

	//좋아요 감소
	@Override
	public int likeDown(int commnum, String userName, int likenum) {
		
		 int result = freelikeCntDAO.freelikedel(likenum, commnum);
		 if(result == 1) {
		   return freeDAO.freelikeDown(commnum);
		 }else {
			 return 0;
		} 
	}

	//수정페이지조회
	@Override
	public FreeVO editdetail(int num) {
		return freeDAO.viewone(num);
	}

	//본인글 조회
	@Override
	public List<FreeVO> namelist(String name, int page) {
		page = (page -1 )*10;
		return freeDAO.nameview(name, page);
	}
	

}
