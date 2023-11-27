/* 작업자 우지원 */



package com.care.bedu.admin.fileManage.vo;

import lombok.Getter;
import lombok.Setter;


import java.util.Date;

import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Alias("fileVO")
public class FileManageVO {

    private int lectDtlNum;             //강의고유번호
    private String lectDtlTitle;        //강의제목
    private int lectDtlTime;            //강의재생시간
    private int lectNum;                //강의번호
    private Date regDate;               //등록날짜
    private int regId;                  //등록유저
    private String lectVideoUrl;        //동영상경로
    private int lectDtlIndex;           //동영상 재생 인덱스

}
