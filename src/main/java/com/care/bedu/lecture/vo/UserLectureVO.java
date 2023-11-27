/* 작업자 우지원 */



package com.care.bedu.lecture.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLectureVO {
    
    private int userLectNum;    // 고유 번호
    private String userName;    // 사용자 이름
    private int lectNum;        // 강의 번호
    private Date lectRegDate;   // 강의 결제일
    
}
