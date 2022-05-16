<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style>
.modify textarea {
	width: calc(100% - 20px);
	height: 110%;
	resize: none;
	padding: 2px 10px;
}

.modify {
	margin-top: 6px;
}

</style>

</head>
<body>
	<c:forEach items="${ list }" var="dto" varStatus="status">
		${ status.first ? '<hr>': ''}
	<%-- 	${ status.index eq 0 ? '<hr>': '' } --%>
		<div data-id="${ dto.id }">
			<div>
				<span>${ dto.name } [${ dto.writedate }]</span>
				<span>
					<c:if test="${ loginInfo.id eq dto.writer }">
					<a class='btn-fill-s btn-modify-save'>수정</a>
					<a class='btn-fill-s btn-delete-cancel'>삭제</a>
					</c:if>
				</span>
			</div>
			<div class='original'>${ fn: replace( fn: replace (dto.content, lf, '<br>'), crlf, '<br>') }</div>
			<div class='modify'>
				
			</div>
		</div>
		<hr>
	</c:forEach>
	
	<script>
		$('.btn-delete-cancel').on('click', function(){
			var $div = $(this).closest('div').parent('div');
			if($(this).text() == '취소'){
				display($div, true);
			} else {
				if (confirm('정말 삭제하시겠습니까?')) {
					$.ajax({
						url: 'comment/delete.bo?id='+$div.data('id'),
						success: function(){
							comment_list();
						},
						error: function(req, text){
							alert(text+": "+req.status);
						}
					});
				}
			}
			
		});
	
		$('.btn-modify-save').on('click', function(){
			var $div = $(this).closest('div').parent('div');
			if ( $(this).text()== '수정') {
				$div.children('.modify').css('height', $div.children('.original').height()-6);
				var tag = '<textarea>' + $div.children('.original').html().replace(/<br>/g, '\n') + '</textarea>';
		
				$div.children('.modify').html( tag );
				$div.children('.modify').children('textarea').focus();
			
				display( $div, false );
				
			} else {
				var comment = { id: $div.data('id'),
								content: $div.children('.modify').children('textarea').val() };
				
				$.ajax({
					url: 'comment/update.bo',
					data: { comment: JSON.stringify(comment)},
					success: function(response){
						alert("변경저장 "+response);
						comment_list();
					},
					error: function(req, text){
						alert(text + ': ' + req.status);
					}
				});
			}			
		});
		
		function display( div, status ){
			div.find('.btn-modify-save').text( status ? '수정' : '저장')
			div.find('.btn-delete-cancel').text( status ? '삭제' : '취소')
			
			div.children('.original').css('display', status ? 'block' : 'none');
			div.children('.modify').css('display', status ? 'none' : 'block');
		}
		
	</script>
</body>
</html>