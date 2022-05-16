<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="css/common.css?<%=new java.util.Date().getTime()%>" />
</head>
<body>
	<%-- <%@ include file="/include/header.jsp" %> --%>
	<jsp:include page="/include/header.jsp" />
	<%-- <c:import url="/include/header.jsp"></c:import> --%>
	<h3>웹서비스 과정 5회차</h3>
	<img src='images/edu.jpg' style='width: 80%'>
	<jsp:include page="/include/footer.jsp" />
</body>
</html>