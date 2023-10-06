package com.gdu.app07.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gdu.app07.dto.AjaxDto;
import com.gdu.app07.service.AjaxService;

import lombok.RequiredArgsConstructor;

@RequestMapping(value="/ajax3")
@RequiredArgsConstructor
@Controller
public class AjaxController3 {

  private final AjaxService ajaxService;
  
  @GetMapping(value="/list.do", produces="application/json; charset=UTF-8")
  public ResponseEntity<List<AjaxDto>> list(){  
    return new ResponseEntity<List<AjaxDto>>( // ResponseEntity는 @ResponseBody를 작성할 필요가 없다.
                  ajaxService.getDtoList()    // 실제 응답 데이터
                , HttpStatus.OK) ;            // 응답 코드 (200)
  }
  
  @PostMapping(value="/detail.do")
  public ResponseEntity<AjaxDto> detail(@RequestBody AjaxDto ajaxDto){  // post 방식(@RequestBody)으로 전송된 JSON 데이터(AjaxDto ajaxDto) 
    // 응답 헤더 : Content-Type (produces="application/json; charset=UTF-8")를 대신
    HttpHeaders header = new HttpHeaders();
    header.add("content-Type", "application/json; charset=UTF-8");
    return new ResponseEntity<AjaxDto>(                  // ResponseEntity는 @ResponseBody를 작성할 필요가 없다.
                  ajaxService.getDto(ajaxDto.getName())  // 실제 응답 데이터
                , header                                 // 응답 헤더
                , HttpStatus.OK);                        // 응답 코드 (200)
  }
  
  
  
  
}
