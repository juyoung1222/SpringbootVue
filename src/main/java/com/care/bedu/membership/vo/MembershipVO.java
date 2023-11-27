/* 작업자 우지원 */



package com.care.bedu.membership.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MembershipVO {
    
    private int subNum;
    private String subName;
    private String subType;
    private Date begin;
    private Date end;
    
}