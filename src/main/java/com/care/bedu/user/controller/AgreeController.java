//작성자 이준원


package com.care.bedu.user.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.care.bedu.user.service.AgreeService;
import com.care.bedu.user.vo.AgreeVO;

@RestController
@RequestMapping("/api")
public class AgreeController {
	private final AgreeService agreeService;
	
	public AgreeController(AgreeService agreeService) {
        this.agreeService = agreeService;
    }
	
	// 이용약관의 번호로 이용약관 리스트 불러오기
    @GetMapping("/agree/{id}")
    public List<AgreeVO> getAgreementById(@PathVariable Long id) {
        return agreeService.getAgreementById(id);
    }
}
