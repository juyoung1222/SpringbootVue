/* 작업자 우지원 */



package com.care.bedu.lecture.service.serviceImpl;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.care.bedu.lecture.dao.LectureCategoryDAO;
import com.care.bedu.lecture.dao.LectureDAO;
import com.care.bedu.lecture.service.LectureService;
import com.care.bedu.lecture.vo.LectureCategoriesVO;
import com.care.bedu.lecture.vo.LectureDetailVO;
import com.care.bedu.lecture.vo.LectureVO;
import com.care.bedu.lecture.vo.UserLectureVO;
import com.care.bedu.review.vo.ReviewVO;


@Service
public class LectureServiceImpl implements LectureService{

	@Autowired
	private LectureDAO lectureDao;

	@Autowired
	private LectureCategoryDAO cateDao;

	
	/* 카테고리별 강의를 들어갔을 때 동영상 조회 */
	@Override
	public ArrayList<Object>  getLectureList(String category) {
		/* 중분류에 따른 소분류 조회 -> 소분류에 따른 강의 목록 조회
		 * -> 자료 구조 정리하여 반환
		 */
		ArrayList<LectureCategoriesVO> dtos = new ArrayList<>(); // 소분류 데이터를 저장할 빈 배열 생성
		dtos = cateDao.getBot(category); // 소분류 카테고리 조회

		HashMap<String, Object> map = new HashMap<>(); // 파라미터를 담을 해쉬맵

		ArrayList<LectureVO> list = new ArrayList<>(); // 강의 목록을 담을 배열

		ArrayList<Object> result = new ArrayList<>(); // 반환 값을 담을 배열
		

		/* 반복문 사용하여 데이터 구조화 */
		for(LectureCategoriesVO dto : dtos){
			HashMap<String,Object> lect = new HashMap<>(); // 구조화된 데이터를 담을 해쉬맵 생성 
			map.put("category", dto.getLectBotCate()); 
			list = lectureDao.getLectureList(map); // 소분류에 따른 강의 목록 조회
			lect.put("lectBotCate", dto.getLectBotCate()); // 소분류 코드
			lect.put("lectBotCateKor", dto.getLectBotCateKor()); // 소분류 한글
			lect.put("item",list); // 소분류 강의 목록
			result.add(lect);
		}

		return result;
	}



	/* 강의 상세 정보 조회 */
	@Override
	public LectureVO getLectureDetail(int num) {
		LectureVO dto = new LectureVO();
		dto = lectureDao.getLectureDetail(num);
		
		return dto;
	}

	/* 해당 강의에 포함되어있는 커리큘럼(동영상 목록)을 조회 */
	@Override
	public ArrayList<LectureDetailVO> getVideoList(int num) {
		ArrayList<LectureDetailVO> list = new ArrayList<>();
		
		list = lectureDao.getVideoList(num);
		
		return list;
	}

	/* 검색을 통한 결과 조회 */
	@Override
	public HashMap<String, ArrayList<LectureVO>> lectureSearch(String keyword, int page) {
		HashMap<String, ArrayList<LectureVO>> map = new HashMap<>();
		ArrayList<LectureVO> dto = new ArrayList<>();
		
		HashMap<String, Object> arg = new HashMap<>(); // 파라미터 맵 생성
		arg.put("keyword", keyword); // 검색 키워드
		arg.put("begin", (page-1)*5); // 시작 인덱스 설정
		dto = lectureDao.lectureSearch(arg);
		map.put("item", dto);

		return map;
	}

	/* 검색했을때 해당 키워드로 조회되는 총 강의 수를 확인 */
	@Override
	public int searchTotal(String keyword) {
		int result = 0;
		result = lectureDao.searchTotal(keyword);
		return result;
	}


	/* 신규 오픈 강좌 4개 조회 */
	@Override
	public HashMap<String, Object> getNewestLecture() {
		HashMap<String, Object> map = new HashMap<>();
		ArrayList<LectureVO> list = new ArrayList<>();

		list = lectureDao.getNewestLecture();

		map.put("item", list);
		return map;
	}



	/* 후기 조회
	 * 아직까지 후기 조회의 기준이 없어서 임시로 최신순으로 조회하도록 구현
	 */
	@Override
	public HashMap<String, Object> getReview(int num) {
		HashMap<String, Object> map = new HashMap<>();

		ArrayList<ReviewVO> result = new ArrayList<>();

		result = lectureDao.getReview(num);
		for(ReviewVO vo : result){
			System.out.println(vo);
		}

		map.put("item", result);

		return map;
	}



	/* 장바구니 담는 메서드*/
	@Override
	public int addToCart(int lectNum, int userNum, String lectName) {
		int result = 0;

		/* 파라미터를 담을 맵 */
		HashMap<String, Object> arg = new HashMap<>();

		// /* 파라미터 맵에 저장 */
		arg.put("lectNum", lectNum);
		arg.put("userNum", userNum);
		arg.put("lectName",lectName);

		/* 장바구니에 담기 */
		result = lectureDao.addToCart(arg);


		return result;
	
	}


	/* 장바구니 조회 */
	@Override
	public HashMap<String, Object> getCart(int num) {
		HashMap<String, Object> result = new HashMap<>();

		ArrayList<LectureVO> carts = new ArrayList<>();

		carts = lectureDao.getCart(num);

		result.put("item", carts);

		return result;
	}


	/* 장바구니 삭제 */
	@Override
	public int removeFromCart(int[] list, int userNum) {
		int result = 0;

		HashMap<String, Object> arg = new HashMap<>();
		arg.put("userNum", userNum);

		for(int i =0; i<list.length;i++){
			arg.put("lectNum", list[i]);
			result = lectureDao.removeFromCart(arg);
			if(result == 0){
				return 0;
			}
		}

		return result;
	}


	/* 동영상 정보 조회 */
	@Override
	public HashMap<String, Object> getLesson(int num, String userName) {
		HashMap<String, Object> map = new HashMap<>();

		LectureDetailVO vo = new LectureDetailVO();

		vo = lectureDao.getLesson(num);

		map.put("lessonItem",vo);


		ArrayList<LectureDetailVO> lessonList = new ArrayList<>();

		lessonList = lectureDao.getVideoList(vo.getLectNum());

		map.put("lessonList",lessonList);

		HashMap<String,Object> args = new HashMap<>();

		args.put("num", vo.getLectNum());
		args.put("userName", userName);

		int count = lectureDao.signUpChk(args);

		if(count == 0){
			map.put("signUp", false);
		} else {
			map.put("signUp", true);
		}

		return map;
	}


	/* 모든 강의 정보 조회 */
	@Override
	public HashMap<String, Object> getAllLectures() {
		HashMap<String,Object> map = new HashMap<>();

		ArrayList<LectureVO> list = new ArrayList<>();
		list = lectureDao.getAllLectures();

		map.put("item", list);
		
		return map;
	}


	/* 수강 목록 저장 */
	@Override
	public int addToMyPage(List<Map<String,Object>> args) {

		int result = 1;
		
		for(Map<String,Object> m : args){
			result = lectureDao.addToMyPage(m);
			if(result != 1){
				return 0;
			}
		}

		return result;
	}


	/* 수강 목록 조회 */
	@Override
	public HashMap<String, Object> getMyPageList(String userName) {
		HashMap<String,Object> map = new HashMap<>();

		ArrayList<UserLectureVO> vo = new ArrayList<>();

		vo = lectureDao.getMyPageList(userName);

		map.put("item", vo);
		
		return map;
	}



	@Override
	public boolean getMyPageChk(String userName, int lectNum) {

		UserLectureVO vo = new UserLectureVO(0, userName, lectNum, null);

		int result = lectureDao.getMyPageChk(vo);

		if(result == 0){
			return false;
		} else{
			return true;
		}

	}

}
