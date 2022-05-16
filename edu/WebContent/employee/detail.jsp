<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<jsp:include page="/include/header.jsp"/>
<h3>사원정보</h3>
<table class='w-px500'>
<tr><th class='w-px120'>사번</th><td>${dto.employee_id}</td></tr>
<tr><th>성명</th><td>${dto.last_name} ${dto.first_name}</td></tr>
<tr><th>이메일</th><td>${dto.email}</td></tr>
<tr><th>전화번호</th><td>${dto.phone_number }</td></tr>
<tr><th>급여</th><td>${dto.salary }</td></tr>
<tr><th>입사일자</th><td>${dto.hire_date }</td></tr>
<tr><th>부서</th><td>${dto.department_name }</td></tr>
<tr><th>업무</th><td>${dto.job_title }</td></tr>
</table>
<div class='btnSet'>
	<a class='btn-fill' href='list.hr'>사원목록</a>
	<a class='btn-fill' href='modify.hr?id=${ dto.employee_id }'>정보변경</a>
	<a class='btn-fill' 
		onclick="if( confirm('정말 삭제?') ) href='delete.hr?id=${ dto.employee_id }'" >정보삭제</a>
	<a class='btn-fill' href='new.hr'>신규사원</a>
</div>

<jsp:include page="/include/footer.jsp"/>
</body>
</html>