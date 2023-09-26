package com.gdu.app01.anno02;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
  
  // @Bean에 name 속성으로 bean의 이름을 등록할 수 있다.
  
  @Bean(name="user")
  public User user() {   // name="user"로 인해서 메소드이름은 의미가 없어진다.
    
    User user = new User();
    
    user.setUserNo(1);
    user.setUserId("admin");
    
    return user;
    
  }
  
  @Bean(name="board")
  public Board board() {
    
    Board board = new Board();
    
    board.setTitle("공지사항");
    board.setEditor(user());
    
    return board;
    
  }

}
