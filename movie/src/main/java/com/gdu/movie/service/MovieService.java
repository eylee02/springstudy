package com.gdu.movie.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

public interface MovieService {
  
  public Map<String, Object> getMovieList();
  public Map<String, Object> getSearchMovieList(HttpServletRequest request, Model model);
  

}
