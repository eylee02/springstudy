package com.gdu.app10.logback;

import java.text.SimpleDateFormat;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.LayoutBase;

public class MyLogbackLayout extends LayoutBase<ILoggingEvent>{
  
  @Override
  public String doLayout(ILoggingEvent event) {
    
    StringBuilder sb = new StringBuilder();
    
    sb.append("[");
    sb.append(new SimpleDateFormat("HH:mm:ss").format(event.getTimeStamp()));
    sb.append("] ");
    sb.append(String.format("%-5s", event.getLevel()));
    sb.append(":");
    String loggerName = event.getLoggerName();
    sb.append(loggerName);
    if(loggerName.equals("jdbc.sqlonly")) {  // sql문을 출력할 때 줄바꿈을 하기위한 if문
      sb.append("\n");
    } else {
    sb.append(" - ");
    }
    sb.append(event.getFormattedMessage());
    sb.append("\n");    
    
    return sb.toString();
  }
  
  
}
