/* 작업자 우지원 */



package com.care.bedu.lecture.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/* 대,중,소 분류에 사용할 VO */
public class LectureCategoriesVO {
   private int lectFildNum;            // 고유번호
   private int lectTopCate;            // 대분류 코드
   private String lectTopCateKor;      // 대분류 텍스트
   private int lectMidCate;            // 중분류 코드
   private String lectMidCateKor;      // 중분류 텍스트
   private int lectBotCate;            // 소분류 코드
   private String lectBotCateKor;      // 소분류 텍스트
   private int parentCode;             // 부모 코드
   private int level;                  // 레벨
   private Date regDate;               // 등록일 
   private String regId;               // 등록자 ID
   private String lectIcon;            // 아이콘 경로
}
