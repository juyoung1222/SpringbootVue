// 작성자 원준용

package com.care.bedu.review.service;

import java.util.HashMap;
import java.util.List;

import com.care.bedu.review.vo.ReviewVO;

public interface ReviewService {
	// 모든 후기를 가져오는 메서드로, page와 size 매개변수로 페이징 처리를 수행합니다.
	List<HashMap<String, Object>> getAllReviews(int page, int size); 
    // 후기를 생성하는 메서드입니다.
    public int createReview(ReviewVO reviewVO);
    
    List<HashMap<String, Object>> getSearchedReviews(String keyword, String topCate);


}