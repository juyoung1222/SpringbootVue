// 작성자 원준용


package com.care.bedu.review.service.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.care.bedu.review.dao.ReviewDAO;
import com.care.bedu.review.service.ReviewService;
import com.care.bedu.review.vo.ReviewVO;

@Service
public class ReviewServiceImpl implements ReviewService {
	
    private final ReviewDAO reviewDAO;

    @Autowired
    public ReviewServiceImpl(ReviewDAO reviewDAO) {
        this.reviewDAO = reviewDAO;
    }
    
    @Override
    public List<HashMap<String, Object>> getAllReviews(int page, int size) {
        int startIndex = (page - 1) * size;
        int endIndex = startIndex + size;

        List<HashMap<String, Object>> allReviews = reviewDAO.getAllReviews();
        if (startIndex > Math.min(endIndex, allReviews.size())) {
            return new ArrayList<>(); // 시작 인덱스가 후기 목록의 범위를 벗어나면 빈 리스트를 반환합니다.
        }

        return allReviews.subList(startIndex, Math.min(endIndex, allReviews.size()));
    }
    
    @Override
    public int createReview(ReviewVO reviewVO) {

        // 후기를 생성하는 로직입니다.
        return reviewDAO.createReview(reviewVO);
    }
    
    @Override
    public List<HashMap<String, Object>> getSearchedReviews(String keyword, String topCate) {
        // 검색 키워드에 기반하여 후기 필터링
        ReviewVO vo = new ReviewVO();
        vo.setKeyword(keyword);
        vo.setTopCate(topCate);
        List<HashMap<String, Object>> sample = reviewDAO.getSearchedReviews(keyword, topCate);
        return reviewDAO.getSearchedReviews(keyword, topCate);
    }


}