/* 작업자 우지원 */



package com.care.bedu.lecture.service;

import com.care.bedu.lecture.vo.LectureHistoryVO;

public interface LectureHistoryService {
    
    /* 동영상 재생 정보 저장 & 업데이트  */
    public int watchHistorySave(String userName, int lectDtlNum, String endTime);

    /* 동영상 재생 정보 조회 */
    public LectureHistoryVO getHistory(String userName, int lectDtlNum);

    /* 동영상 수강 완료 처리 */
    public int setComplete(String userName, int lectDtlNum);
}
