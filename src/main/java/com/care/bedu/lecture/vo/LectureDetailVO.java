/* 작업자 우지원 */



package com.care.bedu.lecture.vo;

import java.sql.Time;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
/* 강의에 포함된 동영상 데이터 VO */
public class LectureDetailVO {
	private int lectDtlNum; // 강의 상세 (동영상) 고유 번호
	private int lectNum; // 참조하는 강의의 고유 번호
	private String lectDtlTitle; // 동영상 이름
	private Time times; // 동영상 재생 시간
	private String lessonUrl; // 동영상 재생 경로
	private int lectDtlIndex; // 동영상 재생 인덱스
}
