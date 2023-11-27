/* 작업자 우지원 */



package com.care.bedu.admin.fileManage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.care.bedu.admin.fileManage.dao.FileManageDao;
import com.care.bedu.admin.fileManage.service.FileManageService;
import com.care.bedu.admin.fileManage.vo.FileManageVO;
import com.care.bedu.lecture.dao.LectureDAO;
import com.care.bedu.lecture.vo.LectureDetailVO;
import com.care.bedu.lecture.vo.LectureVO;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

@Service
public class FileManageServiceImpl implements FileManageService{
    // 파일 업로드 위치
    private String fileBaseDir = "C:/Desktop/LECT/VIDEO/";

    @Autowired
    private FileManageDao fileDao;

    @Autowired
    private LectureDAO lecDao;

    @Override
    public boolean upload(MultipartFile file, int chunkNumber, int totalChunks, FileManageVO vo) throws IOException {

        File dir = new File(fileBaseDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

		// 임시 저장 파일 이름
        String filename = file.getOriginalFilename() + ".part" + chunkNumber;

        Path filePath = Paths.get(fileBaseDir, filename);
        // 임시 저장
        Files.write(filePath, file.getBytes());

		// 마지막 조각이 전송 됐을 경우
        if (chunkNumber == totalChunks-1) {
            String splitStr = file.getOriginalFilename();

            if(splitStr == null){
                return false;
            }
            
            String[] split = splitStr.split("\\.");
            String outputFilename = UUID.randomUUID() + "." + split[split.length-1];
            Path outputFile = Paths.get(fileBaseDir, outputFilename);
            Files.createFile(outputFile);
            
            // 임시 파일들을 하나로 합침
            for (int i = 0; i < totalChunks; i++) {
                Path chunkFile = Paths.get(fileBaseDir, file.getOriginalFilename() + ".part" + i);
                Files.write(outputFile, Files.readAllBytes(chunkFile), StandardOpenOption.APPEND);
                // 합친 후 삭제
                Files.delete(chunkFile);
            }
            
            String videoUrl = outputFile.getFileName().toString();
            
            vo.setLectVideoUrl(videoUrl);
            fileDao.upload(vo);

            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public HashMap<String, Object> getTotalLecture() {

        HashMap<String, Object> map = new HashMap<>();

        ArrayList<LectureVO> list = new ArrayList<>();

        list = fileDao.getTotalLecture();

        map.put("item", list);

        return map;
    }

    @Override
    public boolean deleteFile(int num) {
        boolean result = true;

        LectureDetailVO vo = new LectureDetailVO();

        vo = lecDao.getLesson(num);
        
        String url = fileBaseDir + vo.getLessonUrl();

        File file = new File(url);

        if(file.exists()){
            file.delete();
            if(fileDao.deleteFile(num) != 1){
                result = false;
            };
        } else{
            result = false;
        }

        return result;
    }
}