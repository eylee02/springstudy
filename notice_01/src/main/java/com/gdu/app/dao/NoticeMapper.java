package com.gdu.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.app.dto.NoticeDto;

@Mapper
public interface NoticeMapper {
  
  public int addNotice(NoticeDto noticeDto);
  public List<NoticeDto> getNoticeList();
}
