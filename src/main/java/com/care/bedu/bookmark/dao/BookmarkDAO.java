/* 작업자 우지원 */



package com.care.bedu.bookmark.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestParam;

import com.care.bedu.bookmark.vo.BookmarkVO;

@Mapper
public interface BookmarkDAO {
    
    public int getBookmark(BookmarkVO vo);      // 북마크 여부 조회
    public int save(BookmarkVO vo);             // 북마크 저장
    public int remove(BookmarkVO vo);           // 북마크 삭제
    public int getTotal(String userName);       // 북마크 총 갯수
    
    // 북마크 목록 조회
    public List<BookmarkVO> getList(@RequestParam("userName") String userName, @RequestParam("begin") int begin, @RequestParam("order") String order);       
    public List<BookmarkVO> getNewest(@RequestParam("userName") String userName);       

}
