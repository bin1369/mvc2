<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href='css/common.css?<%=new java.util.Date().getTime()%>'/>
</head>
<body>
<jsp:include page="/include/header.jsp"/>
<h3>신규고객등록</h3>
<form method='post' action='insert.cu' >
<table class='w-px500'>
<tr><th class='w-px120'>고객명</th>
	<td><input type='text' name='name'></td>
</tr>
<tr><th>성별</th>
	<td><label><input type='radio' name='gender' value='남' checked>남</label>
		<label><input type='radio' name='gender' value='여'>여</label>
	</td>
</tr>
<tr><th>이메일</th>
	<td><input type='text' name='email'></td>
</tr>
<tr><th>전화번호</th>
	<td><input type='text' name='phone'></td>
</tr>
</table>
</form>
<div class='btnSet'>
	<a class='btn-fill' onclick="$('form').submit()">고객저장</a>
	<a class='btn-fill' href='list.cu'>취소</a>
</div>
<jsp:include page="/include/footer.jsp"/>
</body>
</html>