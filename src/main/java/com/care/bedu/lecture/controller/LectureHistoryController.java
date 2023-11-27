/* 작업자 우지원 */



package com.care.bedu.lecture.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.care.bedu.lecture.service.serviceImpl.LectureHistoryServiceImpl;
import com.care.bedu.lecture.vo.LectureHistoryVO;

@RestController
@RequestMapping("/api/lecture/history")
public class LectureHistoryController {
    
    @Autowired
    LectureHistoryServiceImpl service;

    @RequestMapping("/save")
    public void watchHistorySave(String userName, int lectDtlNum, String endTime){
        service.watchHistorySave(userName, lectDtlNum, endTime);
    }

    /* 동영상 재생 정보 조회 */
    @RequestMapping("/getHistory")
    public ResponseEntity<LectureHistoryVO> getHistory(String userName, int lectDtlNum){
        return new ResponseEntity<LectureHistoryVO>(service.getHistory(userName, lectDtlNum), HttpStatus.OK);
    }

    /* 강의 수강 완료 */
    @RequestMapping("/setComplete")
    public int setComplete(String userName, int lectDtlNum){
        return service.setComplete(userName, lectDtlNum);
    }
}
