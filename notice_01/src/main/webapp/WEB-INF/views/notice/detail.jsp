<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
</head>
<body>

  <div id="a">
    <h1>공지 상세화면</h1>
    <h3>공지 번호: ${notice.noticeNo}</h3>    <%-- controller에서 notice로 받아옴(detail메소드) --%>
    <h3>구분 : ${notice.gubun == 1 ? '긴급' : '일반'}</h3>
    <h3>제목 : ${notice.title}</h3>
    <h3>내용 : ${notice.content}</h3>
    <div>
      <button type="button" id="btn_edit">편집하러가기</button>
    </div>
  </div>
  <div id="b">
    <div>
      <button type="button" id="btn_back">뒤로가기</button>
    </div>
    <form method="post" action="${contextPath}/notice/save.do">
      <select name="gubun" id="gubun">
        <option value="2">일반</option>
        <option value="1">긴급</option>
      </select>
      <input type="text" name="title" id="title">
      <input type="text" name="content" id="content">
      <button>작성완료</button>
    </form> 
    <script>
      $('#gubun').val('${notice.gubun}');
      $('#title').val('${notice.title}');
      $('#content').val('${notice.content}');
    </script>
  </div>
  
  <script>
  	
  	// 초기 화면
  	$('#a').show();
  	$('#b').hide();
  	
  	// 편집하러가기 클릭
  	$('#btn_edit').click(function(){
  	  $('#a').hide();
  	  $('#b').show();
  	})
  	
  	// 뒤로가기 클릭
  	$('#btn_back').click(function(){
  	  $('#a').show();
  	  $('#b').hide();
  	})
  </script>
  

</body>
</html>