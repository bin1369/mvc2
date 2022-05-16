<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
.login {
	width: 300px;  margin: 20px auto; 
	border:1px solid #aaa; padding: 30px 50px; 
}
.login h3 {  }
.login li { margin: 10px 0 }
.login input { 
	width: 100%; box-sizing: border-box; 
	border: 1px solid #ccc;  
	height: 38px
}
.login [type=button] { background-color: #3367d6; color:#fff;  border:none; }
.center {
	position: absolute;
	left: 50%; top: 50%;
	transform: translate(-50%, -50%); 
}
.login hr { margin: 30px 0 }
#naver {
	background: url("images/naver.png") center; background-size: 100%; 
}
#kakao {
	background: url("images/kakao.png") center; background-size: 100%; 
}
</style>
<link rel="stylesheet" href='css/common.css?<%=new java.util.Date().getTime()%>'>
<script src='js/jquery.js'></script>
</head>
<body>

<div class='center'>
	<a href='<c:url value="/" />'><img src="images/logo.png"></a>
	<div class='login'>
		<h3>로그인</h3>
		<ul>
			<li><input type='text' id='id' class='chk' title='아이디' placeholder="아이디"></li>
			<li><input type='password' id='pw' class='chk' title='비밀번호' placeholder="비밀번호"></li>
			<li><input type='button' id='login' value='로그인'></li>
			<li><hr></li>
			<li><input type='button' id='naver' onclick='location="naverlogin.mb"' title='네이버로그인' ></li>
			<li><input type='button' id='kakao' onclick='location="kakaologin.mb"' title='카카오로그인' ></li>
		</ul>		
	</div>

</div>
<script src='js/common.js?<%=new java.util.Date().getTime()%>'></script>
<script>
$('#pw').on('keyup', function(e){
	if( e.keyCode==13 ) go_login(); 
});
$('#login').on('click', function(){
	go_login();
});
function go_login(){
	if( emptyCheck() ){
		//로그인처리
		$.ajax({
			url: 'edulogin.mb',
			data: { id:$('#id').val(), pw:$('#pw').val() },
			success: function( response ){
				if( JSON.parse(response) ){
					location = '<c:url value="/"/>';					
				}else{
					alert('아이디나 비밀번호가 일치하지 않습니다');
				}
				
			},error: function(req, text){
				alert(text+':'+req.status);
			}
		});
	}
}
</script>
</body>
</html>





