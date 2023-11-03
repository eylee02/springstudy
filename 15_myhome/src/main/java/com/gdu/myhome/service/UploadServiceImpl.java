package com.gdu.myhome.service;

import java.io.File;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.myhome.dao.UploadMapper;
import com.gdu.myhome.dto.AttachDto;
import com.gdu.myhome.dto.UploadDto;
import com.gdu.myhome.dto.UserDto;
import com.gdu.myhome.util.MyFileUtils;
import com.gdu.myhome.util.MyPageUtils;

import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;

@Transactional
@RequiredArgsConstructor
@Service
public class UploadServiceImpl implements UploadService {
  private final UploadMapper uploadMapper;
  private final MyFileUtils myFileUtils;
  private final MyPageUtils myPageUtils;
  
  @Override
  public boolean addUpload(MultipartHttpServletRequest multipartRequest) throws Exception {
    
    String title = multipartRequest.getParameter("title");
    String contents = multipartRequest.getParameter("contents");
    int userNo = Integer.parseInt(multipartRequest.getParameter("userNo"));
    
    UploadDto upload = UploadDto.builder()
                              .title(title)
                              .contents(contents)
                              .userDto(UserDto.builder()
                                          .userNo(userNo)
                                          .build())
                              .build();
    int uploadCount = uploadMapper.insertUpload(upload);
    
    List<MultipartFile> files = multipartRequest.getFiles("files");
    
    
    int attachCount;
    if(files.get(0).getSize() == 0) {
      attachCount = 1;
    } else {
      attachCount = 0;
    }
    
    for(MultipartFile multipartFile : files) {
      if(multipartFile != null && !multipartFile.isEmpty()) {
        String path = myFileUtils.getUploadPath();
        
        File dir = new File(path);
        if(!dir.exists()) {
          dir.mkdirs();
        }
        String originalFilename = multipartFile.getOriginalFilename();
        String filesystemName = myFileUtils.getFilesystemName(originalFilename);
        File file = new File(dir, filesystemName);
        
        multipartFile.transferTo(file);   // 첨부파일이 저장되는 곳
        
        String contentType = Files.probeContentType(file.toPath());  // 이미지의 Content-Type : image/jpeg, image/png 등 image로 시작한다.
        int hasThumbnail = (contentType != null && contentType.startsWith("image")) ? 1 : 0;
        // 썸네일 만드는 코드
        if(hasThumbnail == 1) {
          File thumbnail = new File(dir, "s_" + filesystemName);  // small 이미지를 의미하는 s_을 덧붙임
          Thumbnails.of(file)
                    .size(100, 100)     // 가로 100px 세로 100px
                    .toFile(thumbnail);
        }
        
        AttachDto attach = AttachDto.builder()
                              .path(path)
                              .originalFilename(originalFilename)
                              .filesystemName(filesystemName)
                              .hasThumbnail(hasThumbnail)
                              .uploadNo(upload.getUploadNo())
                              .build();
        attachCount += uploadMapper.insertAttach(attach);
        
      }
    }
    
    return (uploadCount == 1) && (files.size() == attachCount);
  }
  
  @Transactional(readOnly=true)
  @Override
  public Map<String, Object> getUploadList(HttpServletRequest request) {
    
    Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
    int page = Integer.parseInt(opt.orElse("1"));
    int total = uploadMapper.getUploadCount();
    int display = 9;
    
    myPageUtils.setPaging(page, total, display);
    
    Map<String, Object> map = Map.of("begin", myPageUtils.getBegin()
                                   , "end", myPageUtils.getEnd());
    
    List<UploadDto> uploadList = uploadMapper.getUploadList(map);
    
    return Map.of("uploadList", uploadList
                , "totalPage", myPageUtils.getTotalPage());
  }
  
  @Transactional(readOnly=true)
  @Override
  public void loadUpload(HttpServletRequest request, Model model) {
    
    Optional<String> opt = Optional.ofNullable(request.getParameter("uploadNo"));
    int uploadNo = Integer.parseInt(opt.orElse("0"));
    
    model.addAttribute("upload", uploadMapper.getUpload(uploadNo));
    model.addAttribute("attachList", uploadMapper.getAttachList(uploadNo));
    
  }
  
  @Override
  public ResponseEntity<Resource> download(HttpServletRequest request) {
    
    // 첨부 파일의 정보 가져오기
    int attachNo = Integer.parseInt(request.getParameter("attachNo"));    
    AttachDto attach = uploadMapper.getAttach(attachNo);
    
    // 첨부 파일 File 객체 -> Resource 객체
    File file = new File(attach.getPath(), attach.getFilesystemName());
    Resource resource = new FileSystemResource(file);
    
    // 첨부 파일이 없으면 다운로드 취소
    if(!resource.exists()) {
      return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);   
    }
    
    // 다운로드 횟수 증가하기
    uploadMapper.updateDownloadCount(attachNo);
    
    // 사용자가 다운로드 받을 파일의 이름 결정(User-Agent값에 따른 인코딩 처리)
    String originalFilename = attach.getOriginalFilename();
    String userAgent = request.getHeader("User-Agent");
    
    try {
      // IE
      if(userAgent.contains("Trident")) {
        originalFilename = URLEncoder.encode(originalFilename, "UTF-8").replace("+", " ");
      }
      // Edge
      else if(userAgent.contains("Edg")) {
        originalFilename = URLEncoder.encode(originalFilename, "UTF-8");
      }
      // Other
      else {
        originalFilename = new String(originalFilename.getBytes("UTF-8"), "ISO-8859-1");
      }
      
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    // 다운로드 응답 헤더 만들기
    HttpHeaders header = new HttpHeaders();
    header.add("Content-Type", "application/octet-stream");
    header.add("Content-Disposition", "attachment; filename=" + originalFilename);
    header.add("Content-Length", file.length() + "");
    
    // 응답
    return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
    
  }

  
  
}
