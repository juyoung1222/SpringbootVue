/* 작업자 우지원 */



package com.care.bedu.admin.fileManage.service;


import java.io.IOException;
import java.util.HashMap;

import org.springframework.web.multipart.MultipartFile;

import com.care.bedu.admin.fileManage.vo.FileManageVO;


public interface FileManageService {

    public boolean upload(MultipartFile file, int chunkNumber, int totalChunks, FileManageVO vo) throws IOException;
    public HashMap<String, Object> getTotalLecture();
    public boolean deleteFile(int num);
}
