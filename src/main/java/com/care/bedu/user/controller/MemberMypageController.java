/* 작업자 우지원 */



package com.care.bedu.user.controller;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;


import com.care.bedu.user.service.MemberMypageService;
import com.care.bedu.user.vo.MemberMypageVO;


@RestController
@RequestMapping("/api")
public class MemberMypageController {
    
	@Autowired
    MemberMypageService memberMypageService;
    

    /* 마이페이지 홈 */
    @GetMapping("/mypage")
    public List<MemberMypageVO> getMemberMypage(String userName, Model model){

    	/* 처음에 출력할 수강정보 개수 */
		int numOfLecture = 3;
		
		return memberMypageService.lectureListInfoFirst(userName, numOfLecture);
    }
    
    /* 마이페이지 홈(전체보기 클릭 시 화면이동) */
    @GetMapping("/mypageAll")
    public ResponseEntity<HashMap<String,Object>> getMemberMypageAll(String userName, int page,String order, String group, String keyword) {
    	//pageNum에 값이 없으면 1, 있으면 해당하는 페이지를 가져온다.
    	//화면에 보여줄 수강정보의 수
    	int numOfPage = 9;
    	
    	//현재 페이지 번호를 이용해서 출력될 페이지의 시작번호를 구한다.
    	int startNo = (page - 1) * numOfPage;

		HashMap<String, Object> map = new HashMap<>();


		map.put("list",memberMypageService.getMemberMypageAll(userName, startNo, numOfPage,order, group, keyword)); 
    	
		map.put("count",memberMypageService.getLectureCount(userName, group, keyword));

		return new ResponseEntity<HashMap<String,Object>>(map, HttpStatus.OK);
    }

	@RequestMapping("/getRecentlyViewd")
	public ResponseEntity<Map> getRecentlyViewd(String userName){
		return new ResponseEntity<Map>(memberMypageService.getRecentlyViewd(userName), HttpStatus.OK);
	};
}
