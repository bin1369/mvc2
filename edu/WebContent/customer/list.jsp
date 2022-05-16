<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href='css/common.css' />
</head>
<body>
<jsp:include page="/include/header.jsp"/>
<h3>고객목록</h3>
<table class='w-px500 tb_list'>
<tr><th class='w-px120'>성명</th><th>이메일</th></tr>
<c:forEach items="${list}" var="dto">
<tr><td><a href="detail.cu?id=${dto.id}">${dto.name}</a></td>
	<td>${dto.email}</td>
</tr>
</c:forEach>

</table>
<div class='btnSet'>
	<a class='btn-fill' href='new.cu'>신규고객등록</a>
</div>
<jsp:include page="/include/footer.jsp"/>
</body>
</html>