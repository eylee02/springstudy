<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="dt" value="<%=System.currentTimeMillis() %>" />
    
<jsp:include page="../layout/header.jsp">
  <jsp:param value="${blog.blogNo}번블로그" name="title"/>
</jsp:include>

<div>
  
  <!-- 블로그 상세보기 -->
  <div>
    <h1>${blog.title}</h1>
    <div>작성자 : ${blog.userDto.name}</div>
    <div>조회수 : ${blog.hit}</div>
    <div>작성IP : ${blog.ip}</div>
    <div>작성일 : ${blog.createdAt}</div>
    <div>수정일 : ${blog.modifiedAt}</div>
    <div>
      <button></button>
    </div>
    <div>${blog.contents}</div>
  </div>
  
  <hr>
  <!-- 블로그 댓글 목록 --> 
  
  
</div>
  
<script>
 

</script>
 

<%@ include file="../layout/footer.jsp" %>