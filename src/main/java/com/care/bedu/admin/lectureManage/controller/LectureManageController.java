/* 작업자 우지원 */



package com.care.bedu.admin.lectureManage.controller;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.care.bedu.admin.lectureManage.service.impl.LectureManageServiceImpl;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/admin/lectManage")
public class LectureManageController {


    @Autowired
    LectureManageServiceImpl lectManageService;

    @RequestMapping("/lectInsert")
    public ResponseEntity<String> lectInsert(MultipartHttpServletRequest req, HttpServletResponse res) throws IOException{

        int result = lectManageService.lectInsert(req, res);

        if(result == 1){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @RequestMapping("/ImageUpload")
    public ResponseEntity<Object> imgUpload(MultipartHttpServletRequest req, HttpServletResponse res) throws IOException{
        HashMap<String, Object> map = new HashMap<>();
        map = lectManageService.imgUpload(req, res);

        return new ResponseEntity<Object>(map,HttpStatus.OK);
    }

    @RequestMapping("/lectUpdate")
    public ResponseEntity<String> lectUpdate(MultipartHttpServletRequest req, HttpServletResponse res) throws IOException{

        int result = lectManageService.lectUpdate(req, res);
        if(result == 1){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping("/lectDelete")
    public ResponseEntity<String> lectDelete(int num, String thumbnail, String lectDesc) throws IOException {

        int result = 1;

        result = lectManageService.lectDelete(num, thumbnail, lectDesc);

        if(result ==1){
            return ResponseEntity.ok().build();
        } else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
}
