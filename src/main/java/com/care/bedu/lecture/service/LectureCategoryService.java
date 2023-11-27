/* 작업자 우지원 */



package com.care.bedu.lecture.service;

import java.util.HashMap;

public interface LectureCategoryService {
    
    public HashMap<String, Object> getCategory(); /* 대중소 카테고리 전체 조회 */
    public HashMap<String, Object> getCategoryForLevel();
}
