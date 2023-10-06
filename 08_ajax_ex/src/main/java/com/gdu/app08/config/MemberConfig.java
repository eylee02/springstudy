package com.gdu.app08.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gdu.app08.dto.MemberDto;

@Configuration
public class MemberConfig {

  @Bean
  public MemberDto member1() {
    return new MemberDto(1, "뽀로로", 150, 50);
  }
  
  @Bean
  public MemberDto member2() {
    return new MemberDto(2, "루피", 155, 55);
  }
  
  @Bean
  public MemberDto member3() {
    return new MemberDto(3, "크롱", 140, 40);
  }
  
}
