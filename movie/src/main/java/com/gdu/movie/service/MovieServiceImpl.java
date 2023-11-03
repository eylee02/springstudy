package com.gdu.movie.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.gdu.movie.dao.MovieMapper;
import com.gdu.movie.dto.MovieDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MovieServiceImpl implements MovieService {
  
  private final MovieMapper movieMapper;
  
  @Override
  public Map<String, Object> getMovieList() {
    
    int movieCount = movieMapper.getMovieCount();
    List<MovieDto> list = movieMapper.getMovieList();
    
    return Map.of("message", "전체 " + movieCount + "개의 목록을 가져왔습니다."
                , "list", list
                , "status", 200);
  }
  
  
  @Override
  public Map<String, Object> getSearchMovieList(HttpServletRequest request, Model model) {
    
    int movieCount = movieMapper.searchMovieCount();
    
    String column = request.getParameter("column");
    String searchText = request.getParameter("searchText");
    
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("column", column);
    map.put("searchText", searchText);
    
    List<MovieDto> movieList = movieMapper.searchMovieList(map);
    
    model.addAttribute("movieList", movieList);
    
    return Map.of("message", movieCount + "개의 결과가 있습니다."
                , "column", column
                , "searchText", searchText
                , "status", 200);
  }

}
