package com.gdu.staff.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.staff.dto.StaffDto;
import com.gdu.staff.service.StaffService;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class StaffController {
  
  private final StaffService staffService;
  
  @PostMapping(value="/add.do", produces="application/json")
  public ResponseEntity<Map<String, Object>> add(StaffDto staff){
    return staffService.registerStaff(staff);
  }
  
  @GetMapping(value="/list.json", produces="application/json")
  public String list(Model model) {
    List<StaffDto> staffList = staffService.getStaffList();
    model.addAttribute("staffList", staffList);
    return "index";
  }
  
  @GetMapping(value="/query.json", produces="applicaion/json")
  public String get(@RequestParam(value="sno") String sno, Model model) {
    model.addAttribute("get", staffService.getStaff(sno));
    return "index";
  }
  
  
  
  
}
