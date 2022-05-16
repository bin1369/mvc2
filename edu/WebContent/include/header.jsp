<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<link rel="stylesheet" href='css/common.css?<%=new java.util.Date().getTime()%>'/>
<style>
header {
	padding: 0 100px;	width:calc(100% - 200px);
	display: flex;	justify-content: space-between;	
	border-bottom: 1px solid #aaa;
}
header nav ul { display: flex; justify-content: flex-start; 
	font-size: 19px;	font-weight: bold;
}
header nav li:not(:first-child){ margin-left: 50px; }
header nav li a.active, header nav li a:hover { color:#0040ff;  }
header li a { line-height: 38px; }
</style>    
<script src="js/jquery.js"></script>
<script src="js/common.js?<%=new java.util.Date().getTime()%>"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/js/all.min.js"></script>
<header>
	<nav class='category'>
	<ul> 
		<li><a href="<c:url value='/' />"><img src="images/logo.png"></a></li>
		<li><a href="list.cu" class="${category eq 'cu' ? 'active' : ''}">고객관리</a></li>
		<li><a href="list.hr" class="${category eq 'hr' ? 'active' : ''}">사원관리</a></li>
		<li><a href="list.no" class="${category eq 'no' ? 'active' : ''}">공지사항</a></li>
		<li><a href="list.bo" class="${category eq 'bo' ? 'active' : ''}">방명록</a></li>
		<li><a href="list.qa" class="${category eq 'qa' ? 'active' : ''}">Q&A</a></li>
	</ul>
	</nav>
	<div class='user'>
	<ul>
		<c:if test="${ empty loginInfo }">
		<li><a class='btn-fill' href='login.mb'>로그인</a>
			<a class='btn-fill' href='member.mb'>회원가입</a>
		</li>
		</c:if>
		<c:if test="${ !empty loginInfo }">
		<li><strong>${loginInfo.name}</strong> 님
			<a class='btn-fill' href='logout.mb'>로그아웃</a>
		</li>
		</c:if>
	</ul>
	</div>
</header>








