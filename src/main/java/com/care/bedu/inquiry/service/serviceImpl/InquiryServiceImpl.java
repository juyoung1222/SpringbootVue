//작성자: 황용학


package com.care.bedu.inquiry.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.care.bedu.inquiry.dao.InquiryDAO;
import com.care.bedu.inquiry.service.InquiryService;
import com.care.bedu.inquiry.vo.InquiryVO;

@Service
public class InquiryServiceImpl implements InquiryService {

	@Autowired
	private InquiryDAO inquiryDAO;

	//자주 묻는 게시글 조회
	@Override
	public List<InquiryVO> faqList() throws Exception {
		List<InquiryVO> faqlist = inquiryDAO.faqlist();
		return faqlist;
	}
	
	//자주 묻는 질문 검색 리스트 조회
	@Override
	public List<InquiryVO> faqList(String faqKeyword) throws Exception {
		List<InquiryVO> faqlist = inquiryDAO.faqsearch(faqKeyword);
		System.out.println(faqlist);
		return faqlist;
	}
	
	//게시글 리스트 조회
	@Override
	public List<InquiryVO> list() throws Exception {
	    List<InquiryVO> inquirylist = inquiryDAO.inquirylist();
	    for (InquiryVO inquiry : inquirylist) {
	        int replyCnt = inquiryDAO.getReplyCnt(inquiry.getVocNum());
	        inquiry.setReplyCnt(replyCnt);
	    } 
	    return inquirylist;
	}
	
	//게시글 검색 리스트 조회
	@Override
	public List<InquiryVO> inquiryList(String keyword) throws Exception {
	    List<InquiryVO> inquirylist = inquiryDAO.inquirysearch(keyword);
	    for (InquiryVO inquiry : inquirylist) {
	        int replyCnt = inquiryDAO.getReplyCnt(inquiry.getVocNum());
	        inquiry.setReplyCnt(replyCnt);
	    }
	    return inquirylist;
	}
	
	//게시글 전체 갯수 조회
	@Override
	public int getTotal() {
		return inquiryDAO.getTotal();
	}

	//게시글 등록
	@Override
	public int inquiryWriteSave(InquiryVO inquiryVO) {
		if (inquiryVO.getFileName() == null || inquiryVO.getFileName() == "") {
			inquiryVO.setFileName("");
		}
		if (inquiryVO.getFileType() == null || inquiryVO.getFileType() == "") {
			inquiryVO.setFileType("");
		}
		
//	    // 비밀번호를 BCrypt 해시로 변환하여 설정
//	    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//	    String encodedPassword = passwordEncoder.encode(inquiryVO.getPassword());
//	    inquiryVO.setPassword(encodedPassword);

	    return inquiryDAO.inquiryWriteSave(inquiryVO);
	}

	//유저아이디조회
	@Override
	public ArrayList<InquiryVO> getUserId(String userName) {
		return inquiryDAO.getuserId(userName);
	}

	//글 조회 
	@Override
	public InquiryVO inquiryone(Integer vocNum, String userInput) {
	    InquiryVO inquiryVO = inquiryDAO.inquiryone(vocNum);
	    int replyCnt = inquiryDAO.getReplyCnt(vocNum);
	    inquiryVO.setReplyCnt(replyCnt);

//		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//		String password = inquiryVO.getPassword();
//
//		if (passwordEncoder.matches(userInput, password)) {
//			return inquiryVO;
//		} else {
//			return null;
//		}
	    
	    return inquiryVO;

	}
	
	//글 삭제
	@Override
	public int inquirydelete(Integer vocNum) {
		return inquiryDAO.inquirydelete(vocNum);
	}

}
