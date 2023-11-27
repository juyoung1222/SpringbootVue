/* 작업자 우지원 */



package com.care.bedu.bookmark.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.care.bedu.bookmark.dao.BookmarkDAO;
import com.care.bedu.bookmark.service.BookmarkService;
import com.care.bedu.bookmark.vo.BookmarkVO;

@Service
public class BookmarkServiceImpl implements BookmarkService{

    @Autowired
    BookmarkDAO dao;

    @Override
    public int inOut(int lectNum, String userName) {

        BookmarkVO vo = new BookmarkVO();
        vo.setLectNum(lectNum);
        vo.setUserName(userName);
        
        int check = dao.getBookmark(vo);
        int result = 0;

        if(check > 0){
            dao.remove(vo);
            result = 2;
        } else {
            dao.save(vo);
            result = 1;
        }
        return result;
    }

    @Override
    public List<BookmarkVO> getList(String userName, int begin, String order) {

        return dao.getList(userName, begin, order);
    }

    @Override
    public int getBookmark(int lectNum, String userName) {
        BookmarkVO vo = new BookmarkVO();
        vo.setLectNum(lectNum);
        vo.setUserName(userName);
    
        return dao.getBookmark(vo);
    }

	@Override
	public int getTotal(String userName) {

        return dao.getTotal(userName);
	}

    @Override
    public List<BookmarkVO> getNewest(String userName) {
        return dao.getNewest(userName);
    }
    
}
