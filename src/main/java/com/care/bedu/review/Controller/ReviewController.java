//작성자 원준용

package com.care.bedu.review.Controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.care.bedu.review.service.ReviewService;
import com.care.bedu.review.vo.ReviewVO;

@RestController
@RequestMapping("/api")
public class ReviewController {

    private final ReviewService reviewService;
    
    @Autowired
    public ReviewController(ReviewService reviewService){
        this.reviewService = reviewService;
    }

    
    // GET 요청을 처리하는 엔드포인트로, 모든 후기 목록을 가져옵니다.
    // page와 size는 요청 매개변수로 받아서 페이징 처리를 수행합니다.
    @RequestMapping("/reviews")
    public List<HashMap<String, Object>> getAllReviews(@RequestParam int page, @RequestParam int size) {
        return reviewService.getAllReviews(page, size);
    }
    
    
    // POST 요청을 처리하는 엔드포인트로, 새로운 후기를 생성합니다.
    // 요청 본문에 있는 ReviewVO 객체를 받아서 후기를 생성하고 생성된 후기를 반환합니다.
    @RequestMapping(value = "/reviews/write", method = RequestMethod.POST)
    public int createReview(String content, int rwGrade, int lectNum, String userName) {
        ReviewVO reviewVO = new ReviewVO();
        
        reviewVO.setUserName(userName);
        reviewVO.setContent(content);
        reviewVO.setRwGrade(rwGrade);
        reviewVO.setLectNum(lectNum);

        return reviewService.createReview(reviewVO);
    }
    // GET 요청을 처리하는 엔드포인트로, 검색된 후기 목록을 가져옵니다.
    // keyword는 요청 매개변수로 받아서 검색에 사용합니다.
    @RequestMapping("/reviews/search")
    public List<HashMap<String, Object>> getSearchedReviews(@RequestParam String keyword, String topCate) {
        return reviewService.getSearchedReviews(keyword, topCate);
    }

    

}