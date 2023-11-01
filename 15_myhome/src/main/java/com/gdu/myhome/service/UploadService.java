package com.gdu.myhome.service;

import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface UploadService {
  public int addUpload(MultipartHttpServletRequest multipartRequest) throws Exception;
}
