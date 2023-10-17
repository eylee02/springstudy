package com.gdu.app14.service;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.gdu.app14.dao.MemberMapper;
import com.gdu.app14.dto.MemberDto;
import com.gdu.app14.util.PageUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

  private final MemberMapper memberMapper; 
  private final PageUtil pageUtil;
  
  @Override
  public Map<String, Object> register(MemberDto memberDto, HttpServletResponse response) {
    
    Map<String, Object> map = null;
  
    try {      
      
      int addResult = memberMapper.insertMember(memberDto);
      map = Map.of("addResult", addResult);
           
    } catch(DuplicateKeyException e) {   // UNIQUE 칼럼에 중복 값이 전달된 경우에 발생함
      
      try {
      PrintWriter out = response.getWriter();
      response.setContentType("text/plain");  // 일반 텍스트 응답
      response.setStatus(500);
      out.println("이미 사용중인 아이디입니다.");
      out.flush();
      out.close();
      } catch (Exception e2) {
        e2.printStackTrace();
      }
    
    } catch (Exception e) {
      System.out.println(e.getClass().getName());   // 발생한 예외 클래스의 이름 확인
    }

    return map;
    
  }
  
  @Override
  public Map<String, Object> getMembers(int page) {
      
    int total = memberMapper.getMemberCount();
    int display = 2;
    
    pageUtil.setPaging(page, total, display);
    
    Map<String, Object> map = Map.of("begin", pageUtil.getBegin(), "end", pageUtil.getEnd());
    
    List<MemberDto> memberList = memberMapper.getMemberList(map);
    String paging = pageUtil.getAjaxPaging();
    
    return Map.of("memberList", memberList, "paging", paging);
  }
  
  @Override
  public Map<String, Object> getMember(int memberNo) {  
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("member", memberMapper.getMember(memberNo));
    return map;
  }
  
  @Override
  public Map<String, Object> modifyMember(MemberDto memberDto) {
    int modifyResult = memberMapper.updateMember(memberDto);
    return Map.of("modifyResult", modifyResult);
  }
  
  @Override
  public Map<String, Object> deleteMember(int memberNo) {
    return Map.of("deleteResult", memberMapper.deleteMember(memberNo));
  }
  
  @Override
  public Map<String, Object> removeMembers(String memberNoList) {
    List<String> list = Arrays.asList(memberNoList.split(","));
    return Map.of("removeResult", memberMapper.deleteMembers(list));
  }

}
