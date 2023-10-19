package com.gdu.app.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdu.app.dto.NoticeDto;
import com.gdu.app.service.NoticeService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/notice")
@RequiredArgsConstructor
@Controller
public class NoticeController {
  
  private final NoticeService noticeService;
  
  // 목록 반환
  @GetMapping(value="/list.do")
  public String list(Model model) {  // forward할 데이터는 model에 저장한다.
    List<NoticeDto> noticeList = noticeService.getNoticeList();
    model.addAttribute("noticeList", noticeList);  // forward할 데이터 저장하기
    return "notice/list";  // notice 폴더 아래의 list.jsp로 forward하시오.
  }
  
  // 작성페이지로 이동하게하는 컨트롤러 메소드 (list에서 요청받음)
  @GetMapping(value="/write.do")
  public String write() {
    return "notice/write";
  }
  
  // 작성정보를 받아서 서비스로 전달
  @PostMapping(value="/save.do")
  public String save(NoticeDto noticeDto, RedirectAttributes redirectAttributes) {  // 커맨드 객체  redirect할 데이터는 RedirectAttributes에 저장한다 
    int addResult = noticeService.addNotice(noticeDto);
    redirectAttributes.addFlashAttribute("addResult", addResult);
    return "redirect:/notice/list.do";
  }
  
  // 공지 상세보기
  @GetMapping(value="/detail.do")
  public String detail(@RequestParam int noticeNo, Model model) {
    NoticeDto noticeDto = noticeService.getNotice(noticeNo);
    model.addAttribute("notice", noticeDto);   // 저장하는 이름은 달라도됌
    return "notice/detail";  // notice 폴더 아래 detail.jsp 파일로 notice를 보낸다.
  }
  
  // 공지 수정
  @PostMapping(value="/modify.do")
  public String modify(NoticeDto noticeDto, RedirectAttributes redirectAttributes) {
    int modifyResult = noticeService.modifyNotice(noticeDto);
    redirectAttributes.addFlashAttribute("modifyResult", modifyResult);
    return "redirect:/notice/detail.do?noticeNo=" + noticeDto.getNoticeNo();
  }
  
  // 공지 삭제
  @GetMapping(value="delete.do")
  public String delete(@RequestParam int noticeNo, RedirectAttributes redirectAttributes) {
 // /notice/list.do로 redirect할 때 삭제 결과(0 또는 1)를 보내기 위해서 RedirectAttributes를 사용한다. 삭제 결과에 따른 경고창 출력 코드는 list.jsp에 있다.
    redirectAttributes.addFlashAttribute("deleteResult", noticeService.deleteNotice(noticeNo));
    return "redirect:/notice/list.do";
  }
  
  
}
