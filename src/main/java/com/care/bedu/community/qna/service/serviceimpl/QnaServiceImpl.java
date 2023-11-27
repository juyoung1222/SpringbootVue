package com.care.bedu.community.qna.service.serviceimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.care.bedu.community.qna.dao.LikeCntDAO;
import com.care.bedu.community.qna.dao.QnaDAO;
import com.care.bedu.community.qna.service.QnaService;
import com.care.bedu.community.qna.vo.LikeCntVO;
import com.care.bedu.community.qna.vo.QnaVO;

@Service
public class QnaServiceImpl implements QnaService{
	
	@Autowired private QnaDAO qnaDAO;
	@Autowired private LikeCntDAO likeCntDAO;

	//글조회
	//검색어가 존재하면 검색 쿼리문 실행 아니면 기본 리스트 조회 실행
	@Override
	public List<QnaVO> listProc(QnaVO qnaVO) {
		qnaVO.setPage((qnaVO.getPage()-1)*10);			
		if(qnaVO.getKeyword() != null && qnaVO.getKeyword() != "") {
			return qnaDAO.viewsearch(qnaVO);			
		}
		else {
			return qnaDAO.viewlist(qnaVO);
		}			
	}

	//글등록시 조회수 좋아요 개수 0으로 초기화하여 데이터베이스에 저장
	//글등록시 조회수 조회수 개수 0으로 초기화하여 데이터베이스에 저장
	@Override
	public int boardwrite(QnaVO qnaVO) {
		qnaVO.setQnaCnt(0);			
		qnaVO.setQnaLikeCnt(0);		
		return qnaDAO.viewWrite(qnaVO);
	}

	//게시글 상세보기
	@Override
	public QnaVO viewone(int qnanum, String userName, String regid) {
		qnaDAO.qnaCntUp(qnanum);
		return qnaDAO.viewone(qnanum);
								
	}

	//게시글 삭제
	@Override
	public int viewdelete(QnaVO qnaVO) {
		if(qnaVO.getQnaBdNum() != null && qnaVO.getQnaBdNum() > 0
				&& qnaVO.getUserName() != null && qnaVO.getUserName().equals("ADMIN")) {
			qnaDAO.viewreplydelete(qnaVO);
			qnaDAO.viewansdelete(qnaVO);
			
		}
		return qnaDAO.viewdelete(qnaVO);
	}

	//게시글 수정
	@Override
	public int viewupdate(QnaVO qnaVO) {
		return qnaDAO.viewupdate(qnaVO);					
	}

	// 게시글 전체 개수 조회
	@Override
	public int getTotal() {
		return qnaDAO.getTotal();						
	}
	
	//좋아요 1증가
	@Override
	public HashMap<String, Object> likeUp(int qnanum, String userName, String regId, String likeyns) {
		int likeCnt = qnaDAO.likeName(qnanum, userName, likeyns);
		Integer likeyn = likeCntDAO.getlikenum(qnanum, userName, likeyns);
		HashMap<String, Object> map = new HashMap<>();
		
		if(likeCnt == 0) {
			LikeCntVO likeCntVO = new LikeCntVO();
			likeCntVO.setUserName(userName);
			likeCntVO.setQsBdNum(qnanum);
			likeCntVO.setRegId(regId);
			likeCntVO.setLikeyn(likeyns);
			
			Integer result = likeCntDAO.likeCntSave(likeCntVO);
			
			if(result == 1) {
				Integer getnum = qnaDAO.likeUp(qnanum);
				if(getnum == 1) {
					map.put("likenum", likeyn);
					map.put("result", getnum);
					return map;
				}else {
					map.put("likenum", likeyn);
					map.put("result", getnum);
					return map;
				}
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

	//유저아이디조회
	@Override
	public ArrayList<QnaVO> getUserId(String userName) {
		return qnaDAO.getuserId(userName);
	}

	//좋아요 1감소
	@Override
	public int likeDown(int num, String userName, int likenum) {
		int result = likeCntDAO.likedel(likenum);
		if(result == 1) {
			return qnaDAO.likeDown(num);
		}else {
			return 0;
		}
	}

	//질문글수정페이지 글조회 
	@Override
	public QnaVO qnaEditDetail(int num) {
		return qnaDAO.viewone(num);
	}

	//본인글 조회
	@Override
	public List<QnaVO> namelist(String name, int page) {
		page = (page -1 )*10;
		return qnaDAO.nameview(name, page);
	}


}
