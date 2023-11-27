/* 작업자 우지원 */



package com.care.bedu.admin.lectureManage.dao;

import org.apache.ibatis.annotations.Mapper;

import com.care.bedu.lecture.vo.LectureVO;

@Mapper
public interface LectureManageDao {
    
    public int lectInsert(LectureVO vo);
    public int lectUpdate(LectureVO vo);
    public int lectDelete(int num);
}
