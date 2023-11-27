/* 작업자 우지원 */



package com.care.bedu.bookmark.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookmarkVO {
    
    private int num;            //고유번호
    private String userName;    //사용자이름
    private int lectNum;        //강의번호
    private Date bookmarkDate;  //북마크 일시
    private String title;       //강의제목
    private String lectSum;     //강의요약
    private String teacher;     //강사이름
    private int lectPeriod;     //수강기간
    private String thumbnail;   //썸네일경로
    private int score;          //평점
    private int scoreUsers;     //평점 작성 유저 수
    private int users;          //학습자 수

}
