package com.gdu.myhome.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gdu.myhome.service.UserService;

import lombok.RequiredArgsConstructor;

 // @EnableScheduling  appconfig에 기재되어있음
@RequiredArgsConstructor
@Component
public class InactiveUserBatch {

  private final UserService userService;
  
  @Scheduled(cron="0 0 0 1/1 * ?")  // 매일 자정에 동작(년도 * 는 뺌)
  public void execute() {
    userService.inactiveUserBatch();
  }
}
