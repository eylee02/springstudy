package com.gdu.app04.ctrl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MvcController {
  
  // DispatcherServlet(servlet-context.xml)에서 ViewResolver를 제거했으므로 JSP의 전체 경로를 작성해줘야 한다.

  @RequestMapping(value="/", method=RequestMethod.GET)
  public String main() {
    return "/WEB-INF/main.jsp";
  }
  
}
