package com.gdu.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NoticeDto {
  private int noticeNo;
  private int gubun;
  private String title;
  private String content;
}
