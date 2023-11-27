package com.care.bedu.community.freeBoard.dao;

import org.apache.ibatis.annotations.Mapper;
import com.care.bedu.community.qna.vo.LikeCntVO;
  
  @Mapper 
  public interface FreeLikeCntDAO {
  
	  //좋아요 클릭 여부 확인
	  public int getfreelikeName(int commnum, String userid, String likeyn);
	  
	  //좋아요 데이터 삭제
	  public int freelikedel(int likenum, int commnum);
	  
	  //좋아요 번호 조회
	  public Integer getlikeFreenum(int commNum, String userName, String likeyns);
	  
	  //좋아요 데이터 추가
	  public int likeCntFreeSave(LikeCntVO likeCntVO);
	  
	  //조회수 데이터 존재여부 확인
	  public int freeEqcnt(int commnum, String username);
	  
	  //조회수 데이터 삽입
	  public int cntFreeSave(LikeCntVO likeCntVO);
  
  }
 
