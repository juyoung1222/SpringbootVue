/* 작업자 우지원 */



package com.care.bedu.lecture.dao;

import org.apache.ibatis.annotations.Mapper;

import com.care.bedu.lecture.vo.LectureHistoryVO;

@Mapper
public interface LectureHistoryDAO {
    
    /* 동영상 재생 정보 저장 & 업데이트  */
    public int watchHistorySave(LectureHistoryVO vo);

    /* 동영상 재생 정보 조회 */
    public LectureHistoryVO getHistory(LectureHistoryVO vo);

    /* 동영상 수강 완료 */
    public int setComplete(LectureHistoryVO vo);

}
