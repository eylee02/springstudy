/**
 * 회원 가입 페이지
 */
 
   
  /* 함수 호출 */
  $(() => {
	  fnCheckEmail();
	  fnJoin();
  })
  
  /* 전역 변수 선언 */
  var emailPassed = false;
  
  /* 함수 정의 */
  const getContextPath = () => {
  let begin = location.href.indexOf(location.host) + location.host.length;
  let end = location.href.indexOf('/', begin + 1);
  return location.href.substring(begin, end);
  }
  
  /* 이메일 중복체크 후 인증메일 발송하는 함수 */
  const fnCheckEmail = () => {
	  $('#btn_get_code').click(() => {
		  
		  let email = $('#email').val();

			// 연속된 ajax() 함수 호출의 실행 순서를 보장하는 JavaScript 객체 Promise
		  new Promise((resolve, reject) => {
			  
			  // 성공했다면 resolve() 함수 호출 -> then() 메소드에 정의된 화살표 함수 호출
			  // 실패했다면 reject() 함수 호출 -> catch() 메소드에 정의된 화살표 함수 호출
			  
			  // 1. 정규식 검사
			  let regEmail = /^[A-Za-z0-9-_]+@[A-Za-z0-9]{2,}([.][A-Za-z]{2,6}){1,2}$/;
			  if(!regEmail.test(email)){
				  reject(1);
				  return;
			  }
			  
			  // 2. 이메일 중복 체크
			  $.ajax({
				  // 요청
				  type: 'get',
				  url: getContextPath() + '/user/checkEmail.do',
				  data: 'email=' + email,
				  // 응답
				  dataType: 'json',
				  success: (resData) => {  // resData === {"enableEmail": true}
  				  if(resData.enableEmail){
						  resolve();
					  } else {
						  reject(2);
					  }
				  }
			  })
			  
		  }).then(() => {
			  
			  // 3. 인증코드 전송
			  $.ajax({
				  // 요청
				  type: 'get',
				  url: getContextPath() + '/user/sendCode.do',
				  data: 'email=' + email,
				  // 응답
				  dataType: 'json',
				  success: (resData) => {  // resData === {"code": "6자리코드"}
				  	alert(email + "로 인증코드가 전송되었습니다.");
				    $('#code').prop('disabled', false);              /* 인증코드 입력란 활성화(처음에는 비활성화되어있음)*/
				    $('#btn_verify_code').prop('disabled', false);   /* 인증하기 버튼 활성화 */
				  	$('#btn_verify_code').click(() => {
				      emailPassed = $('#code').val() === resData.code; /* 입력된 인증코드와 발급코드가 같으면 emailPassed에 true 값 저장 */
				      if(emailPassed){
				        alert('이메일이 인증되었습니다.');
				      } else {
				        alert('이메일 인증이 실패했습니다.');
				      }
				    })
				  }
			  })
			  
		  }).catch((state) => {
			  emailPassed = false;
			  switch(state){
			  case 1: $('#msg_email').text('이메일 형식이 올바르지 않습니다.'); break;
			  case 2: $('#msg_email').text('이미 가입한 이메일입니다. 다른 이메일을 입력해 주세요.'); break;
			  }
		  })
	  })
	  
  }
  
  /* 이메일 인증이 완료되지 않으면 join화면으로 넘어가지 못하게 막는 함수 */
  const fnJoin = () => {
    $('#frm_join').submit((ev) => {
      if(!emailPassed) {
    	alert('이메일을 인증 받아야 합니다.');
        ev.preventDefault();
        return;
      }
    })
  } 
  