//작성자 원준용

package com.care.bedu.review.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.care.bedu.review.vo.ReviewVO;

@Mapper
public interface ReviewDAO {
	// 특정 후기를 ID로 가져오는 메서드입니다.
    ReviewVO getReviewById(int id);
    
    // 후기를 생성하는 메서드입니다.
    int createReview(ReviewVO reviewVO);
    
    // 모든 후기를 가져오는 메서드입니다.
    List<HashMap<String, Object>> getAllReviews();
    
    // 검색된 후기를 가져오는 메서드입니다.
    List<HashMap<String, Object>> getSearchedReviews(String keyword, String topCate);

    List<HashMap<String, Object>> getSearchedReviews(List<HashMap<String, Object>> sample);

}