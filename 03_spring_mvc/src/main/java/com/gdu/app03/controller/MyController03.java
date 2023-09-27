package com.gdu.app03.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyController03 {

  @RequestMapping("/blog/detail.do")  // GET 방식의 method는 생략할 수 있다. value만 작성할 땐 value= 부분도 생략할 수 있다. 
  public String blogDetail(HttpServletRequest request) {
    // ViewResolver의 prefix : /WEB-INF/views/
    // ViewResolver의 suffix : .jsp
    String blogNo = request.getParameter("blogNo");
    request.setAttribute("blogNo", blogNo);
    return "blog/detail";   // /WEB-INF/views/blog/detail.jsp로 forward한다.
  }
  
  
}
