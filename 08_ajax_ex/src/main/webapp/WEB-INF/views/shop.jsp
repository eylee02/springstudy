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
<script>




</script>
</head>
<body>

  <div>
    <h1>주말에 풀어보기</h1>
  </div>
  <form id="frm_search">
  <div>검색결과건수
    <select id="search">
      <option selected>10</option>
      <option>20</option>
      <option>30</option>
    </select>
  </div>
  <div class="sort">
    <input type="radio" name="sort" id="sort_sim">
    <label for="sort_sim">유사도순</label>
    <input type="radio" name="sort" id="sort_date">
    <label for="sort_date">날짜순</label>
    <input type="radio" name="sort" id="sort_lprice">
    <label for="sort_lprice">낮은가격순</label>
    <input type="radio" name="sort" id="sort_hprice">
    <label for="sort_hprice">높은가격순</label>
  </div>
  <div class="searchText">검색어 입력
    <input type="text" id="searchT" name="searchT">
    <button type="submit">검색</button>
  </div>
  </form>
  <hr>
  <div>
    <table border="1">
    <thead>
      <tr>
        <td>상품명</td>
        <td>썸네일</td>
        <td>최저가</td>
        <td>판매처</td>
      </tr>
    </thead>
    <tbody id="search_list"></tbody>
    </table>
  
  
  </div>
  


</body>
</html>