package com.gdu.myhome.service;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.myhome.dao.BlogMapper;
import com.gdu.myhome.dto.BlogDto;
import com.gdu.myhome.dto.BlogImageDto;
import com.gdu.myhome.util.MyFileUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BlogServiceImpl implements BlogService {
  
  private final MyFileUtils myFileUtils;
  private final BlogMapper blogMapper;
  
  @Override
  public Map<String, Object> imageUpload(MultipartHttpServletRequest multipartRequest) {
    
    // 이미지가 저장될 경로
    String imagePath = myFileUtils.getBlogImagePath();
    File dir = new File(imagePath);
    if(!dir.exists()) {
      dir.mkdirs();
    }
    
    // 이미지 파일 (CKEditor는 이미지를 upload라는 이름으로 보내므로 바꿀수 없음)
    MultipartFile upload = multipartRequest.getFile("upload");
    
    // 이미지가 저장될 이름
    String originalFilename = upload.getOriginalFilename();
    String filesystemName = myFileUtils.getFilesystemName(originalFilename);
    
    // 이미지 File 객체
    File file = new File(dir, filesystemName);
    
    // 저장을 수행
    try {
      upload.transferTo(file);
    } catch (Exception e) {
      e.printStackTrace();
    }    
    
    // CKEditor로 저장된 이미지의 경로를 JSON 형식으로 반환해야 함(전달되는 이름(uploaded,url)은 못바꿈)
    return Map.of("uploaded", true
                , "url", multipartRequest.getContextPath() + imagePath + "/" + filesystemName);
    
    // url: "http://localhost:8080/myhome/blog/2023/10/27/파일명"
    // servlet-context.xml에 
    // /blog/** (만능문자처리) 주소 요청을 /blog 디렉터리로 연결하는 <resources> 태그를 추가해야 함.
    // location에 "file://"붙여아함 => "file:///blog/"
  }
  
  @Override
  public int addBlog(HttpServletRequest request) {
    
    // BLOG_T에 추가할 데이터
    String title = request.getParameter("title");
    String contents = request.getParameter("contents");
    int userNo = Integer.parseInt(request.getParameter("userNo"));
    String ip = request.getRemoteAddr();    // request에서 ip주소가져오기 .getRemoteAddr()
    // BlogDto 생성
    BlogDto blog = BlogDto.builder()
                      .title(title)
                      .contents(contents)
                      .userNo(userNo)
                      .ip(ip)
                      .build();
    // BLOG_T에 추가
    // BlogMapper에 insertBlog() 메소드를 실행하면
    // insertBlog() 메소드로 전달한 blog 객체에 blogNo값이 저장된다.
    int addResult = blogMapper.insertBlog(blog);
    
    // BLOG 작성시 사용한 이미지 목록 (Jsoup 라이브러리 사용 => HTML을 분석해서 태그추출)
    Document document = Jsoup.parse(contents);
    Elements elements = document.getElementsByTag("img");
    
    if(elements != null) {
    for(Element element : elements) {
      String src = element.attr("src");
      String filesystemName = src.substring(src.lastIndexOf("/") + 1); 
      BlogImageDto blogImage = BlogImageDto.builder()
                                    .blogNo(blog.getBlogNo())
                                    .imagePath(myFileUtils.getBlogImagePath())
                                    .filesystemName(filesystemName)
                                    .build();
      blogMapper.insertBlogImage(blogImage);
      }   
    }
    return addResult;
  }
  
  public void blogImageBatch() {
    
    // 1. 어제 작성된 블로그의 이미지 목록 (DB)
    List<BlogImageDto> blogImageList = blogMapper.getBlogImageInYesterday();
    
    // 2. List<BlogImageDto> -> List<Path> (Path는 경로+파일명으로 구성) => lambda 함수 사용
    List<Path> blogImagePathList = blogImageList.stream()
                                                .map(blogImageDto -> new File(blogImageDto.getImagePath(), blogImageDto.getFilesystemName()).toPath())
                                                .collect(Collectors.toList());
    
    // 3. 어제 저장된 블로그 이미지 목록 (디렉토리)
    File dir = new File(myFileUtils.getBlogImagePathInYesterday());
    
    // 4. 삭제할 File 객체들
    File[] targets = dir.listFiles(file -> !blogImagePathList.contains(file.toPath()));

    // 5. 삭제
    if(targets != null && targets.length != 0) {
      for(File target : targets) {
        target.delete();
      }
    }
    
  }
  
  
}
