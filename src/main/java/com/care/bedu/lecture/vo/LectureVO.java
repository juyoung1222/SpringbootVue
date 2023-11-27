/* 작업자 우지원 */



package com.care.bedu.lecture.vo;

import java.sql.Time;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/* 강의 목록에 사용할 VO */
public class LectureVO {
	private int lectNum; // 강의 고유번호
	private String title; // 강의 이름
	private String teacher; // 강사명
	private int total; // 강의 갯수
	private int lectPeriod; // 수강 기간
	private String thumbnail; // 썸네일 이미지 경로
	private int lectCnt; // 조회수
	private String regDate; // 강의 개설일
	private String price; // 가격
	private String lectDesc; // 강의 상세정보
	private int likeYn; // 유저별 강의 좋아요 여부
	private double score; // 강의 평점
	private int scoreUsers; // 강의 평점 매긴 사람
	private Time totalTimes; // 총 강의 시간
	private String category; // 강의 카테고리 --> 코드 혹은 영어로 저장
	private String korCategory; // 강의 카테고리 --> 한글로 저장
	private String lectSum; // 강의 설명 요약
	private int regNum; // 강의 등록자 계정 고유 번호
	private String updateDate; // 최근 수정일
	private int updateNum; // 최근 수정자 번호
	private int progress; // 강의 진행 퍼센트
}
