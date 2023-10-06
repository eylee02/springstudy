package com.gdu.app07.controller;

import java.io.File;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AjaxController4 {
  
  @GetMapping(value="/ajax4/display.do", produces="application/octet-stream")  // "application/octet-stream" : 이진 데이터(0, 1) 자체를 의미한다.
  public ResponseEntity<byte[]> display(@RequestParam("path") String path
                                      , @RequestParam("filename") String filename) {
    
    ResponseEntity<byte[]> responseEntity = null;
    
    try {
      
      // File 객체
      File file = new File(path, filename);
      
      // File 객체를 byte[]로 변환하는 Spring 클래스
      byte[] b = FileCopyUtils.copyToByteArray(file);
      
      // ResponseEntity 객체 생성
      responseEntity = new ResponseEntity<byte[]>(b, HttpStatus.OK);
      
      
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    return responseEntity;
    
  }
}
