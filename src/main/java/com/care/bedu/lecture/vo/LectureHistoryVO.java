/* 작업자 우지원 */



package com.care.bedu.lecture.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LectureHistoryVO {
    
    private int num; // 고유번호
    private String userName; // 시청 사용자 이름
    private int lectDtlNum; // 수강한 동영상
    private Date historyRecordDay; // 조회한 일시
    private int endTime; // 마지막 재생 타임
    private int complete; // 동영상 수강 완료 여부
}
