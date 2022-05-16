<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/common.css"/>
</head>
<body>
<jsp:include page="/include/header.jsp"/>
<h3>[ ${dto.name} ] 고객정보변경</h3>
<form method='post' action='update.cu'>
<input type='hidden' name='id' value='${dto.id}'>
<table class='w-px500'>
<tr><th class='w-px120'>성별</th>
	<td><label><input type='radio' name='gender' value='남'
				 ${dto.gender eq '남' ? 'checked' : ''} >남</label>
		<label><input type='radio' name='gender' value='여'
				<c:if test="${dto.gender eq '여'}">checked</c:if> >여</label>
	</td>
</tr>
<tr><th>이메일</th>
	<td><input type='text' name='email' value='${dto.email}'></td>
</tr>
<tr><th>전화번호</th>
	<td><input type='text' name='phone' value='${dto.phone}'></td>
</tr>
</table>
</form>
<div class='btnSet'>
	<a class='btn-fill' onclick="$('form').submit()">저장</a>
	<a class='btn-fill' href="detail.cu?id=${dto.id}">취소</a>
</div>

<jsp:include page="/include/footer.jsp"/>
</body>
</html>