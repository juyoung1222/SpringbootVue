/* 작업자 우지원 */



package com.care.bedu.lecture.service;

import java.util.*;

import com.care.bedu.lecture.vo.LectureDetailVO;
import com.care.bedu.lecture.vo.LectureVO;


public interface LectureService {
	
	public ArrayList<Object> getLectureList(String category); // 분야별 강의 목록
	public LectureVO getLectureDetail(int num); // 강의 상세 정보
	public ArrayList<LectureDetailVO> getVideoList(int num); /* 강의 상세 커리큘럼 동영상 */
	public HashMap<String,ArrayList<LectureVO>> lectureSearch(String keyword, int page); /* 동영상 목록 검색 */
	public int searchTotal(String keyword); /* 검색어에 따른 총 데이터 갯수 조회 */
	public HashMap<String, Object> getNewestLecture(); /* 신규 오픈 강좌 4개 조회 */
	public HashMap<String, Object> getReview(int num); /* 강의 하단에 보여줄 수강 후기 최신순 5개 조회 */
	public int addToCart(int lectNum, int userNum, String lectName); /* 장바구니에 추가 */
	public HashMap<String, Object> getCart(int num); /* 장바구니 조회 */
	public int removeFromCart(int[] list, int userNum); /* 장바구니 삭제 */
	public HashMap<String, Object> getLesson(int num, String userName); /* 동영상 조회 */
	public HashMap<String, Object> getAllLectures(); /* 동영상 조회 */
	public int addToMyPage(List<Map<String,Object>> args); /* 결제 후 수강 목록에 추가 */
	public HashMap<String, Object> getMyPageList(String userName); /* 수강 목록 조회 */
	public boolean getMyPageChk(String userName, int lectNum); /* 수강 목록에 있는지 강의번호, 사용자 이름을 기준으로 체크 */
}
