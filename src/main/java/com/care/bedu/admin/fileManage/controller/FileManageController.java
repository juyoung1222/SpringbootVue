/* 작업자 우지원 */



package com.care.bedu.admin.fileManage.controller;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.care.bedu.admin.fileManage.service.FileManageService;
import com.care.bedu.admin.fileManage.vo.FileManageVO;


@RestController
@RequestMapping(value = "/api/admin/fileManage")
public class FileManageController {
    
    @Autowired
    private FileManageService service;

    @GetMapping(value = "/upload.do")
    public ModelAndView upload(jakarta.servlet.http.HttpServletRequest request, ModelAndView mv) {
        mv.setViewName("/file/fileUpload");
        return mv;
    }

    @PostMapping("/uploadFormAction")
	public ResponseEntity<String> uploadFormPost(MultipartHttpServletRequest request, Model model) throws IOException{
        MultipartFile file = request.getFile("videoFile");
        int chunkNumber = Integer.parseInt(request.getParameter("chunkNumber")) ;
        int totalChunk = Integer.parseInt(request.getParameter("totalChunk"));
        int userNum = Integer.parseInt(request.getParameter("userNum"));
        String lectDtlTitle = request.getParameter("lectDtlTitle");
        int lectDtlTime = Integer.parseInt(request.getParameter("lectDtlTime"));
        int lectNum = Integer.parseInt(request.getParameter("lectNum"));
        int lectDtlIndex = Integer.parseInt(request.getParameter("lectDtlIndex"));

        FileManageVO vo = new FileManageVO();
        vo.setLectDtlTitle(lectDtlTitle);
        vo.setLectDtlTime(lectDtlTime);
        vo.setLectNum(lectNum);
        vo.setRegId(userNum);
        vo.setLectDtlIndex(lectDtlIndex);


        service.upload(file, chunkNumber, totalChunk, vo);
        if(chunkNumber == totalChunk-1){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).build();
        }
    }  

    @RequestMapping("/getTotalLecture")
    public ResponseEntity<Object> getTotalLecture(){
        HashMap<String, Object> map = new HashMap<>();

        map = service.getTotalLecture();

        return new ResponseEntity<Object>(map, HttpStatus.OK);
    }

    @RequestMapping("/deleteFile")
    public ResponseEntity<String> deleteFile(int num){
        boolean check = false;

        check = service.deleteFile(num);

        if(check){
            return ResponseEntity.ok().build();
        } else{
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).build();
        }
    }
}
