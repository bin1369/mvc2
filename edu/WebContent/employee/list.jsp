<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href='css/common.css?<%=new java.util.Date().getTime()%>'/>
<style>
.tb-wrap { overflow-y:auto; margin:0 auto; height:419px; }
.tb-wrap .tb_list { width: 100%; }
</style>
</head>
<body>
<jsp:include page="/include/header.jsp"/>
<h3>사원목록</h3>

<div class='w-px800 text-right' style='margin:20px auto;'>
<form method='post' action='list.hr'>
<select name='department_id' onchange="$('form').submit()">
	<option value=''>부서명</option>
	<c:forEach items="${departments}" var="d">
	<option  ${department_id eq d.department_id ? 'selected' : ''}
	value='${d.department_id}'>${d.department_name}</option>
	</c:forEach>
</select>
</form>
</div>

<div class='tb-wrap w-px800'>
<table class='w-px800 tb_list'>
<tr><th class='w-px80'>사번</th><th class='w-px180'>성명</th>
	<th class='w-px120'>입사일자</th><th class='w-px150'>부서</th><th>업무</th></tr>
<c:forEach items="${list}" var="dto">
<tr><td>${dto.employee_id }</td>
	<td><a href='detail.hr?id=${dto.employee_id}'>${dto.last_name} ${dto.first_name}</a></td>
	<td>${dto.hire_date}</td>
	<td>${dto.department_name }</td>
	<td class='text-left'>${dto.job_title}</td>
</tr>
</c:forEach>
</table>
</div>

<jsp:include page="/include/footer.jsp"/>

</body>
</html>





