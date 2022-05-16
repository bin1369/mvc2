<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
table td { text-align: left }
</style>
</head>
<body>
<jsp:include page="/include/header.jsp" />
<h3>신규사원등록</h3>
<form method="post" action="insert.hr">
<table class='w-px500'>
<tr><th class='w-px120'>성명</th>
	<td><input type='text' class='w-px120' placeholder="성" name='last_name'>
		<input type='text' class='w-px120' placeholder="명" name='first_name'>
	</td></tr>
<tr><th>이메일</th>
	<td><input type='text' name='email'></td></tr>
<tr><th>전화번호</th>
	<td><input type='text' name='phone_number'></td></tr>
<tr><th>급여</th>
	<td><input type='text' name='salary' ></td></tr>
<tr><th>입사일자</th>
	<td><input type='date' name='hire_date'
		value='<fmt:formatDate pattern="yyyy-MM-dd" 
					value="<%=new java.util.Date() %>" />' ></td></tr>
<tr><th>부서</th>
	<td><select name='department_id'>
		<option value='0'>부서선택</option>
		<c:forEach items="${departments}" var="d">
		<option value='${d.department_id}'>${d.department_name}</option>
		</c:forEach>
		</select>
	</td></tr>
<tr><th>업무</th>
	<td><select name='job_id'>
		<c:forEach items="${jobs}" var="j">
		<option value='${j.job_id}'>${j.job_title}</option>
		</c:forEach>
		</select></td></tr>
</table>
</form>
<div class='btnSet'>
	<a class='btn-fill' onclick='$("form").submit()'>저장</a>
	<a class='btn-empty' href='list.hr'>취소</a>
</div>
<jsp:include page="/include/footer.jsp" />
</body>
</html>