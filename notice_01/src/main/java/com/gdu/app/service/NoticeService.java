package com.gdu.app.service;

import java.util.List;

import com.gdu.app.dto.NoticeDto;

public interface NoticeService {
  
  public int addNotice(NoticeDto noticeDto);
  public List<NoticeDto> getNoticeList();
  
}
