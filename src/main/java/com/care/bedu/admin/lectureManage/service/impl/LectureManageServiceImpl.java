/* 작업자 우지원 */



package com.care.bedu.admin.lectureManage.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.care.bedu.admin.lectureManage.dao.LectureManageDao;
import com.care.bedu.admin.lectureManage.service.LectureManageService;
import com.care.bedu.lecture.vo.LectureVO;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class LectureManageServiceImpl implements LectureManageService{

    private String baseUrl = "C:/Desktop/";
    private String httpUrl = "http://172.30.1.85:8081/";
    private String thumbnailPath = "LECT/THUMB/";
    private String descPath = "LECT/DESC/";
    @Autowired
    LectureManageDao manageDao;

    @Override
    public HashMap<String, Object> imgUpload(MultipartHttpServletRequest req, HttpServletResponse res) throws IOException {

        HashMap<String, Object> map = new HashMap<>();

        MultipartFile multiFile = req.getFile("upload");

        if(multiFile == null){
            return null;
        }
        String originName = multiFile.getOriginalFilename();

        if(originName == null){
            return null;
        }

        String extension = originName.split("\\.")[1];

        String uid = UUID.randomUUID().toString() + "." + extension;
        String uploadUrl = baseUrl+ descPath + uid;
        
        File file = new File(uploadUrl);
        
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }

        InputStream input = multiFile.getInputStream();
        OutputStream output = new FileOutputStream(file);
        byte[] bytes = new byte[1024];
        
        while(input.read(bytes) != -1){
            output.write(bytes);
        }

        input.close();
        output.flush();
        output.close();

        map.put("url", httpUrl+ descPath +uid);


        return map;
    }

    @Override
    public int lectInsert(MultipartHttpServletRequest req, HttpServletResponse res)
            throws IOException {
            

            MultipartFile thumbnail = req.getFile("thumbnail");

            LectureVO lectureVO = new LectureVO();

            String title = req.getParameter("title");
            String teacher = req.getParameter("teacher");
            String price = req.getParameter("price");
            int period = Integer.parseInt(req.getParameter("period")) ;
            String summary = req.getParameter("summary");
            String contents = req.getParameter("contents");
            String cateCode = req.getParameter("cateCode");
            String cateKor = req.getParameter("cateKor");
            int regNum = Integer.parseInt(req.getParameter("regNum"));

            lectureVO.setTitle(title); lectureVO.setTeacher(teacher);
            lectureVO.setPrice(price); lectureVO.setLectPeriod(period);
            lectureVO.setLectSum(summary); lectureVO.setLectDesc(contents);
            lectureVO.setCategory(cateCode); lectureVO.setRegNum(regNum);
            lectureVO.setKorCategory(cateKor);

            if(thumbnail == null){
                return 0;
            }
            String originName = thumbnail.getOriginalFilename();

            if(originName == null){
                return 0;
            }
            String extension = originName.split("\\.")[1];

            String uid = UUID.randomUUID().toString() + "." + extension;

            String uploadUrl = baseUrl + thumbnailPath + uid;

            File file = new File(uploadUrl);

            if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }

            InputStream input = thumbnail.getInputStream();
            OutputStream output = new FileOutputStream(file);
            byte[] bytes = new byte[1024];

            while(input.read(bytes) != -1){
                output.write(bytes);
            }

            input.close();
            output.flush();
            output.close();

            String returnPath = httpUrl + thumbnailPath + uid;

            lectureVO.setThumbnail(returnPath);

            int result = 1;

            result = manageDao.lectInsert(lectureVO);

            return result;
    }

    @Override
    public int lectUpdate(MultipartHttpServletRequest req, HttpServletResponse res) throws IOException {
        int result = 0;

        MultipartFile multipartFile = req.getFile("newThumbnail");

        String title = req.getParameter("title");
        int lectNum = Integer.parseInt(req.getParameter("lectNum"));
        String teacher = req.getParameter("teacher");
        String price = req.getParameter("price");
        int period = Integer.parseInt(req.getParameter("period"));
        String contents = req.getParameter("contents");
        String summary = req.getParameter("summary");
        int updateNum = Integer.parseInt(req.getParameter("updateNum"));

        LectureVO vo = new LectureVO();

        vo.setTitle(title); vo.setLectNum(lectNum); vo.setTeacher(teacher);
        vo.setPrice(price); vo.setLectPeriod(period); vo.setLectDesc(contents);
        vo.setLectSum(summary); vo.setUpdateNum(updateNum);
        
        // 새로운 썸네일 업로드시
        if(multipartFile != null){

            String oldThumbnail = req.getParameter("oldThumbnail");
            String[] splits = oldThumbnail.split("/");
            String oldOriginName = splits[splits.length-1];
            String oldFilePath = baseUrl + thumbnailPath + oldOriginName;
            
            File oldFile = new File(oldFilePath);
            
            // 기존의 썸네일 파일 삭제
            if(oldFile.exists()){
                oldFile.delete();
            }

            String originName = multipartFile.getOriginalFilename();

            if(originName == null){
                return 0;
            }
            String extension = originName.split("\\.")[1];

            String uid = UUID.randomUUID().toString() + "." + extension;

            String uploadUrl = baseUrl + thumbnailPath + uid;
            
            File newFile = new File(uploadUrl);

            if(!newFile.getParentFile().exists()){
                newFile.getParentFile().mkdirs();
            }

            InputStream input = multipartFile.getInputStream();
            OutputStream output = new FileOutputStream(newFile);
            byte[] bytes = new byte[1024];

            while(input.read(bytes) != -1){
                output.write(bytes);
            }

            output.flush();
            output.close();
            input.close();

            String returnPath = httpUrl + thumbnailPath + uid;

            vo.setThumbnail(returnPath);

        }
        result = manageDao.lectUpdate(vo);

        return result;
    }

    @Override
    public int lectDelete(int num, String thumbnail, String lectDesc) throws IOException {
        
        String[] splits = thumbnail.split("/");

        String oldOriginName = splits[splits.length -1];

        String originPath = baseUrl + thumbnailPath + oldOriginName;

        File file = new File(originPath);

        if(file.exists()){
            file.delete();
        }

        int result = 1;

        result = manageDao.lectDelete(num);
        
        return result;
    }
    
}
