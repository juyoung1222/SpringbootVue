/* 작업자 우지원 */



package com.care.bedu.admin.fileManage.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.care.bedu.admin.fileManage.vo.FileManageVO;
import com.care.bedu.lecture.vo.LectureVO;


@Mapper
public interface FileManageDao {

    public int upload(FileManageVO param);
    public ArrayList<LectureVO> getTotalLecture();
    public int deleteFile(int num);
}
