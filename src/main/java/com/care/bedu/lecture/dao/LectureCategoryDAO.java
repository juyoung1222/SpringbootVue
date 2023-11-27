/* 작업자 우지원 */



package com.care.bedu.lecture.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.care.bedu.lecture.vo.LectureCategoriesVO;

@Mapper
public interface LectureCategoryDAO {
    public ArrayList<LectureCategoriesVO> getCategories();
    public ArrayList<LectureCategoriesVO> getBot(String mid);

    /* 대, 중, 소 레벨별로 전체 조회하기 */
    public ArrayList<LectureCategoriesVO> getTopLevel();
    public ArrayList<LectureCategoriesVO> getMidLevel();
    public ArrayList<LectureCategoriesVO> getBotLevel();
}
