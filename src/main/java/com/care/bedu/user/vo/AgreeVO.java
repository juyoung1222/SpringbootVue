//작성자 이준원


package com.care.bedu.user.vo;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AgreeVO {
	// 이용약관 번호
	private Long id;
	// 이용약관 제목
    private String title;
    // 이용약관 내용
    private String content;
    // 이용약관 작성날짜
    private LocalDateTime createdAt;

}
