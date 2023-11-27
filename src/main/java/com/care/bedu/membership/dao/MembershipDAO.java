/* 작업자 우지원 */



package com.care.bedu.membership.dao;

import org.apache.ibatis.annotations.Mapper;

import com.care.bedu.membership.vo.MembershipVO;

@Mapper
public interface MembershipDAO {

	/* 구독 저장 */
	public int getSub(MembershipVO vo); /* 구독 저장 */

	/* 구독 정보 조회 */
	public MembershipVO getSubInfo(String nickname);
}
