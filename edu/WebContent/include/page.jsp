<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    

<div class='page-list'>
<c:if test='${page.curBlock gt 1 }'>
<a title='처음' onclick='go_page(1)'><i class="fas fa-angle-double-left"></i></a>
<a title='이전' onclick='go_page(${page.beginPage-page.blockPage})'><i class="fas fa-angle-left"></i></a>
</c:if>

<c:forEach var="no" begin="${page.beginPage}" end="${page.endPage}">
<c:if test='${no eq page.curPage}'>
	<span class='page-on'>${no}</span>
</c:if>
<c:if test='${no ne page.curPage}'>
	<a onclick='go_page(${no})'>${no}</a>
</c:if>
</c:forEach>

<!-- 다음블럭/마지막블럭: 마지막블럭이 아닌 경우만 -->
<c:if test='${page.curBlock ne page.totalBlock}'>
<a title='다음' onclick='go_page(${page.endPage+1})'><i class="fas fa-angle-right"></i></a>
<a title='마지막' onclick='go_page(${page.totalPage})'><i class="fas fa-angle-double-right"></i></a>
</c:if>

</div>  

<script>
function go_page(no){
	$('[name=curPage]').val(no);
	$('form').submit();
}
</script>








  