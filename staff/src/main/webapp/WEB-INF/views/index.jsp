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
 
   $(() => {
    fnRegisterStaff();
    fnCheckStaffNo();
    fnCheckDept();
   })
   
   // 사원 등록 함수
   const fnRegisterStaff = () => {
     $('#btn_register').click(() => {
       $.ajax({
    	 type: 'post',
    	 url: '${contextPath}/add.do',
    	 data: $('#frm_register').serialize(),
    	 dataType: 'json',
    	 success: (resData) => {
    	   if(resData.addResult === 1){
    	     alert('사원 등록이 성공했습니다.');
    	     // fnGetStaffList();
    	     // fnInit();
    	   } else {
    		 alert('사원 등록이 실패했습니다.');
    	   }
    	 },
    	 error: (jqXHR) => {
    	   if(jqXHR.responseJSON.addResult === 0){
    		 alert('사원 등록이 실패했습니다.');
    	   }
    	 }
       })
     })
   }
   
   const fnGetStaffList = () =>{
	  
   }
   
   const fnCheckStaffNo = () => {
	   $('#sno').keyup((ev) => {
		 let sno = $(ev.target).val();
		 let regSno = /^[0-9]{5}$/;
		 snoPassed = regSno.test(sno);
		 if(!snoPassed){
			alert('사원번호는 5자리 숫자입니다.');
		 }
	   })
   }
   
   const fnCheckDept = () => {
	  $('#dept').keyup((ev) => {
		let dept = $(ev.target).val();
		let regDept = /^[ㄱ-ㅎ가-힣]{5}$/;
		if(regDept.test(dept)){
			alert('부서는 3~5자리 한글입니다.');
		}
	  })
   }
   
   
 
</script>
</head>
<body>

  <div>
    <h1>사원등록</h1>
    <div>
      <form id="frm_register" method="post">
        <input type="text" name="sno" id="sno" placeholder="사원번호">
        <input type="text" name="name" id="name" placeholder="사원명">
        <input type="text" name="dept" id="dept" placeholder="부서명">
        <button type="button" id="btn_register">등록</button>
      </form>
    </div>
  </div>   

  <hr>
  
  <div>
    <h1>사원조회</h1>
    <div>
      <input type="text" name="query" id="query" placeholder="사원번호입력">
      <button type="button" id="btn_query">조회</button>
      <button type="button" id="btn_list">전체</button>
    </div>
    
   <hr>
   
   <div>
    <h1>사원목록</h1>
    <div>
      <table border="1">
        <thead>
          <tr>
            <td>사원번호</td>
            <td>사원명</td>
            <td>부서명</td>
            <td>연봉</td>
          </tr>
        </thead>
        <tbody id="staff_list">
          <c:forEach items="${staffList}" var="staff">
            <tr>
              <td>${staff.sno}</td>
              <td>${staff.name}</td>
              <td>${staff.dept}</td>
              <td>${staff.salary}</td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
   </div>
   
   
   
  </div>  

</body>
</html>