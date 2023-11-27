/* 작업자 우지원 */



package com.care.bedu.bookmark.service;

import java.util.List;

import com.care.bedu.bookmark.vo.BookmarkVO;

public interface BookmarkService {
    
    /** 북마크 저장 & 삭제
     * 저장 - 1 반환
     * 삭제 - 2반환 */ 
    public int inOut(int lectNum, String userName);         
    
    //북마크 목록 조회
    public List<BookmarkVO> getList(String userName, int begin, String order);       

    // 북마크 여부 조회
    public int getBookmark(int lectNum, String userName);

    /** 북마크 갯수 조회 */
    public int getTotal(String userName);

    /** 대시보드 최신 북마크 3개만 조회 */
    public List<BookmarkVO> getNewest(String userName);
}
