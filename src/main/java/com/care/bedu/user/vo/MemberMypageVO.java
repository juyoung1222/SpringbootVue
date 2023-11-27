/* 작업자 우지원 */



package com.care.bedu.user.vo;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberMypageVO {

	private int userlectnum;         /* 사용자강의순번 */
	private String userid;           /* 강의듣는 회원 */
	private int userNum;			/* 강의듣는 회원 구분 번호 */
	private int lectnum;          	/* 강의순번    */
	private String lectnm;           /* 강의이름    */
	private LocalDate lectregdate;   /* 강의신청날짜 */
	private LocalDateTime regdate;   /* 등록일자   */
	private String regid;            /* 등록ID   */
	private String title;
	private String lectDesc;
	private int lectPeriod;
}
