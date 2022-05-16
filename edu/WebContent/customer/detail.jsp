<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/common.css"/>
</head>
<body>
<jsp:include page="/include/header.jsp"/>
<h3>[ ${dto.name} ] 고객정보</h3>
<table class='w-px500'>
<tr><th class='w-px120'>성별</th>
	<td>${dto.gender}</td>
</tr>
<tr><th>이메일</th>
	<td>${dto.email}</td>
</tr>
<tr><th>전화번호</th>
	<td>${dto.phone}</td>
</tr>
</table>
<div class='btnSet'>
	<a class='btn-fill' href='list.cu'>고객목록</a>
	<a class='btn-fill' href='new.cu'>신규고객</a>
	<a class='btn-fill' href='modify.cu?id=${dto.id}'>정보변경</a>
	<a class='btn-fill'
		onclick="if(  confirm('정말 삭제?') ) href='delete.cu?id=${dto.id}' " >정보삭제</a>
</div>
<jsp:include page="/include/footer.jsp"/>
</body>
</html>