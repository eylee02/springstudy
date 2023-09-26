package com.gdu.app02.anno02;

import java.sql.Connection;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class MyJdbcDao {
  
  private Connection con;
  private MyJdbcConnection myJdbcConnection;
  
  public Connection getConnection() {
    AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
    myJdbcConnection = ctx.getBean("myJdbcConnection", MyJdbcConnection.class);
    ctx.close();
    return myJdbcConnection.getConnection();
  }
  
  private void close() {
    try {
      if(con != null) {
        con.close();
      }
    }catch (Exception e) {
        e.printStackTrace();
      }
    }
  
  public void add() {
    con = getConnection();
    System.out.println("add() 호출");
    close();
  }
  
  
  }


