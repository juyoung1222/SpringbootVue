/* 작업자 우지원 */



package com.care.bedu.membership.service;

import java.util.HashMap;

public interface MembershipService {
	
	/* 구독자 등록 */
	public int getSubscribe(String nickname, String type);
	
	/* 구독 정보 조회 */
	public HashMap<String, Object> getSubInfo(String nickname);

}
