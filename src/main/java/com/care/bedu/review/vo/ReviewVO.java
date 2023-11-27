// 작성자 원준용

package com.care.bedu.review.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewVO {
    private String category;
    private String korCategory;
    private String topCate;
    private String keyword;
    private String cateKor;
    private String userName;
    private String content;
    private int rwGrade;
    private int lectNum;
    private Date rwDate;
    private Date regDate;
    private String regId;
}
