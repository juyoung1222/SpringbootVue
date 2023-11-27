/* 작업자 우지원 */



package com.care.bedu.lecture.service.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.care.bedu.lecture.dao.LectureCategoryDAO;
import com.care.bedu.lecture.service.LectureCategoryService;
import com.care.bedu.lecture.vo.LectureCategoriesVO;

@Service
public class LectureCategoryServiceImpl implements LectureCategoryService{

    @Autowired
    LectureCategoryDAO dao;

    @Override
    public HashMap<String, Object> getCategory() { /* 대중소 카테고리 조회 */
        HashMap<String, Object> map = new HashMap<>();

        ArrayList<LectureCategoriesVO> list = new ArrayList<>();
        list = dao.getCategories();

        map.put("item", list);

        return map;
    }

    @Override
    public HashMap<String, Object> getCategoryForLevel() {
        HashMap<String, Object> map = new HashMap<>();

        ArrayList<LectureCategoriesVO> top = new ArrayList<>();
        ArrayList<LectureCategoriesVO> mid = new ArrayList<>();
        ArrayList<LectureCategoriesVO> bot = new ArrayList<>();
        
        top = dao.getTopLevel();
        mid = dao.getMidLevel();
        bot = dao.getBotLevel();

        map.put("top", top); map.put("mid", mid); map.put("bot", bot);

        return map;
    }

}
