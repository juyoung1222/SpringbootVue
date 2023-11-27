/* 작업자 우지원 */



package com.care.bedu.lecture.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.care.bedu.lecture.service.LectureService;
import com.care.bedu.lecture.vo.LectureDetailVO;
import com.care.bedu.lecture.vo.LectureVO;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/lect")
public class LectureController {
	
	@Autowired
	private LectureService lectureService;

	/* 강의 리스트 조회 
	 * 매개 변수	: String 카테고리
	 * 반환 값		: 강의 목록
	 * */
	@RequestMapping("/lectureList")
	public HashMap<String, Object> getLectureList(String category){
		ArrayList<Object> list = new ArrayList<>();
		HashMap<String, Object> map = new HashMap<>();
		list = lectureService.getLectureList(category);
		
		map.put("item", list);
		return map;
	}
	
	/* 강의 상세정보 조회 
	 * 매개 변수	: int 강의 번호
	 * 반환 값		: 강의 상세 정보
	 * */
	@RequestMapping("/lectureDetail")
	public LectureVO getLectureDetail(int num) {
		LectureVO dto = new LectureVO();
		
		dto = lectureService.getLectureDetail(num);
		
		return dto;
	}

	/* 신규 오픈 강좌 4개 조회 
	 * 매개 변수	
	 * 반환 값		: 최신순 강의 4개
	 * */
	@RequestMapping("/getNewestLecture")
	public HashMap<String, Object> getNewestLecture(){
		HashMap<String, Object> map = new HashMap<>();

		map = lectureService.getNewestLecture();

		return map;
	}
	
	
	/* 강의 동영상 목록 조회 
	 * 매개 변수	: 강의 번호
	 * 반환 값		: 동영상 목록
	 * */
	@RequestMapping("/getVideoList")
	public ArrayList<LectureDetailVO> getVideoList(int num){
		ArrayList<LectureDetailVO> list = new ArrayList<>();
		
		list = lectureService.getVideoList(num);
		
		return list;
	}

	/* 검색화면 조회 
	 * 매개 변수	: String 검색어, int 페이지
	 * 반환값		: 검색된 강의 목록
	 * */
	@RequestMapping("/lectureSearch")
	public HashMap<String,ArrayList<LectureVO>> lectureSearch(String keyword, int page){
		HashMap<String, ArrayList<LectureVO>> map = new HashMap<>();
		
		map = lectureService.lectureSearch(keyword, page);
		
		return map;
	}

	/* 검색화면 조회시 토탈 갯수 
	 * 매개 변수	: String 키워드
	 * 반환 값		: int 검색된 강의 총 갯수
	 * */
	@RequestMapping("/searchTotal")
	public int searchTotal(String keyword){
		int total = lectureService.searchTotal(keyword);
		return total;
	}

	/* 강의 후기 조회 
	 * 매개 변수	: int 강의 번호
	 * 반환 값		: 후기 목록 5개
	 * */
	@RequestMapping("/getReview")
	public HashMap<String, Object> getReview(int num){
		HashMap<String, Object> map = new HashMap<>();

		map = lectureService.getReview(num);

		return map;
	}

	/* 결제하기 버튼 사용시 장바구니에 추가 
	 * 매개 변수	: int 강의 번호, int 사용자 번호, String 강의명
	 * 반환 값		: 성공 여부
	 * */
	@RequestMapping("/addToCart")
	public int addToCart(int lectNum, int userNum, String lectName){
		int result = 0;
		result = lectureService.addToCart(lectNum, userNum,lectName);
		return result;
	}

	/* 회원번호 기준으로 장바구니 조회 
	 * 매개 변수	: int 사용자 번호
	 * 반환 값		: 장바구니 목록
	 * */
	@RequestMapping("/getCart")
	public HashMap<String, Object> getCart(int userNum){
		HashMap<String, Object> result = new HashMap<>();
		result = lectureService.getCart(userNum);

		return result;
	}

	/* 장바구니에서 삭제 
	 * 매개 변수	: int[] 강의 번호 목록, int 사용자번호
	 * 반환 값		: 성공 여부
	 * */
	@RequestMapping("/removeFromCart")
	public ResponseEntity<HttpStatus> removeFromCart(int[] list,int userNum){ 
	
		int result = 0;

		result = lectureService.removeFromCart(list, userNum);

		if(result == 0){
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}

	/* 동영상 재생, 수강 여부 확인 
	 * 매개변수	: int 강의 번호, String 사용자 이름
	 * 반환 값		: 동영상 데이터, 수강 여부 데이터
	 * */
	@RequestMapping("/getLesson")
	public HashMap<String,Object> getLesson(int num, String userName){

		return lectureService.getLesson(num, userName);
	}

	/* 모든 강의 조회 조건X 
	 * 매개 변수	:
	 * 반환 값		: 모든 강의 목록
	 * */
	@RequestMapping("/getAllLectures")
	public HashMap<String, Object> getAllLectures(){

		return lectureService.getAllLectures();
	}

	/* 강의 결제 후, 수강 목록에 추가 
	 * 매개 변수	: String userName(사용자명), int lectNum(강의 번호), String lectName(강의명)
	 * 반환 값		: 성공 여부
	 * */
	@RequestMapping("/addToMyPage")
	public int addToMyPage(@RequestBody List<Map<String,Object>> args){


		return lectureService.addToMyPage(args);
	}

	/* 수강 목록 조회 
	 * 매개 변수	: 강의명
	 * 반환 값		: 수강 목록
	 * */
	@RequestMapping("/getMyPageList")
	public HashMap<String,Object> getMyPageList(String userName){

		return lectureService.getMyPageList(userName);
	}

	/*
	 *수강 목록 조회 ( 사용자 이름, 강의 번호 기준)
	 * 매개 변수	: String 사용자 이름, int 강의 번호
	 * 반환 값		: boolean 수강 여부
	 */
	@RequestMapping(value="/getMyPagetChk", method=RequestMethod.GET)
	public ResponseEntity<Boolean> requestMethodName(@RequestParam String userName, int lectNum) {
		boolean result = lectureService.getMyPageChk(userName,lectNum);
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	}
}
