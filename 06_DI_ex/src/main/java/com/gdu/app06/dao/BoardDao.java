package com.gdu.app06.dao;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.gdu.app06.dto.BoardDto;

public class BoardDao {

  private BoardDto boardDto1;
  private BoardDto boardDto2;
  private BoardDto boardDto3; 
  
  // Setter 형식을 이용한 @Autowired, 생성자형식은 config파일에서 생성자로bean을 생성해서 사용하지못함
  @Autowired
  public void setBoardDao(BoardDto boardDto1, BoardDto boardDto2, BoardDto boardDto3) {
    this.boardDto1 = boardDto1;
    this.boardDto2 = boardDto2;
    this.boardDto3 = boardDto3;
  }

  public List<BoardDto> getBoardList(){
    return Arrays.asList(boardDto1, boardDto2, boardDto3);
  }
  
  public BoardDto getBoardByNo(int boardNo) {
    BoardDto boardDto = null;
    if(boardDto1.getBoardNo() == boardNo) {
      boardDto = boardDto1;
    } else if(boardDto2.getBoardNo() == boardNo){
      boardDto = boardDto2;
    } else if(boardDto3.getBoardNo() == boardNo){
      boardDto = boardDto3;
    }
    return boardDto;
  }
  
}
