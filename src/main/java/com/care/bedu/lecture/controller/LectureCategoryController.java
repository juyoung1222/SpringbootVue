/* 작업자 우지원 */



package com.care.bedu.lecture.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.care.bedu.lecture.service.serviceImpl.LectureCategoryServiceImpl;



@RestController
@RequestMapping("/api/lect")
public class LectureCategoryController {

    @Autowired
    LectureCategoryServiceImpl lectCateService;

    /* 대중소 카테고리 전체 조회 */
    @RequestMapping("/getCategory")
    public HashMap<String,Object> getCategory(){
        return lectCateService.getCategory();
    }

    /* 대중소 각각 조회하여 반환 */
    @RequestMapping("/getCategoryForLevel")
    public ResponseEntity<Object> getCategoryForLevel(){
        HashMap<String, Object> map = new HashMap<>();

        map = lectCateService.getCategoryForLevel();

        return new ResponseEntity<Object>(map, HttpStatus.OK);
    }
}
