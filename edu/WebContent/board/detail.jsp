<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<jsp:include page="/include/header.jsp" />
	<h3>방명록 상세</h3>
	<table class='w-px1000'>
		<tr>
			<th class='w-px120'>제목</th>
			<td colspan='5' class='text-left'>${dto.title }</td>
		</tr>
		<tr>
			<th>작성자</th>
			<td>${dto.name }</td>
			<th class='w-px120'>작성일자</th>
			<td class='w-px120'>${ dto.writedate }</td>
			<th class='w-px80'>조회수</th>
			<td class='w-px80'>${ dto.readcnt }</td>
		</tr>
		<tr>
			<th>내용</th>
			<td colspan='5' class='text-left'>${fn: replace(dto.content, crlf, '<br>') }</td>
		</tr>
		<tr>
			<th>첨부파일</th>
			<td colspan='5' class='text-left'>${dto.filename }
				<a id='preview'></a> 
				<c:if test="${ !empty dto.filename }">
					<a href='download.bo?id=${dto.id}'>
						<i class="font-b fas fa-download"></i>
					</a>
				</c:if>
			</td>
		</tr>
	</table>
	<div class='btnSet'>
		<a class='btn-fill' onclick="url_link('list.bo')">목록으로</a>
		<!-- 로그인한 사용자가 작성한 글인 경우만 변경/삭제 가능 -->
		<c:if test="${loginInfo.id eq dto.writer}">
			<a class='btn-fill' onclick="url_link('modify.bo')">정보변경</a>
			<a class='btn-fill'
				onclick="if( confirm('정말 삭제?') ) url_link('delete.bo')">정보삭제</a>
		</c:if>
	</div>

	<!-- 댓글입력부분 -->
	<div class='comment'>
		<div id="comment_regist">
			<div class='head'>
				<span><strong>댓글작성</strong></span> <span><a
					class='btn-fill-s' onclick="comment_regist()">댓글저장</a></span>
			</div>
			<textarea id="comment"></textarea>
		</div>
		<div id='comment_list' class='text-left'></div>
	</div>



	<script>
		// 댓글저장
		function comment_regist() {
			if (JSON.parse('${empty loginInfo}')) {
				alert('댓글을 저장하려면 로그인하세요!');
				return;
			} else if ($('#comment').val() == '') {
				alert('댓글을 입력하세요!');
				$('#comment').focus();
				return;
			}

			$.ajax({
				url : 'comment/insert.bo',
				data : {
					board_id : '${dto.id}',
					content : $('#comment').val(),
					writer : '${loginInfo.id}'
				},
				success : function(response) {
					if (JSON.parse(response)) {
						alert('댓글이 저장되었습니다');
						$('#comment').val('');
						comment_list();
					} else {
						alert('댓글 등록 실패!');
					}

				},
				error : function(req, text) {
					alert(text + ':' + req.status);
				}
			});

		}
		
		$(function(){
			comment_list();	
		});
		
		function comment_list() {
			$.ajax({
				url : 'comment/list.bo',
				data : {
					board_id : '${dto.id}'
				},
				success : function(response) {
					console.log(response);
					$('#comment_list').html(response);
				},
				error : function(req, text) {
					alert(text + ":" + req.status);
				}

			});
		}

		function url_link(url) {
			$('form').attr('action', url);
			$('form').submit();
		}
	</script>

	<form method='post'>
		<input type='hidden' name='id' value='${dto.id}'> <input
			type='hidden' name='curPage' value='${page.curPage}'> <input
			type='hidden' name='pageList' value='${page.pageList}'> <input
			type='hidden' name='search' value='${page.search}'> <input
			type='hidden' name='keyword' value='${page.keyword}'> <input
			type='hidden' name='viewType' value='${page.viewType}'>
	</form>

	<div id='popup-background'
		onclick="$('#popup-background, #popup').css('display', 'none');"></div>
	<div id='popup' class='center'></div>

	<script>
		// 첨부된 파일이 있고, 그 파일이 이미지파일이라면 미리보기되게
		if (JSON.parse('${!empty dto.filename }')) {
			if (isImage('${dto.filename }')) {
				$('#preview').html(
						'<img src="${dto.filepath}" id="preview-img">');
			}
		}

		$(document).on('click', '#preview-img', function() {
			$('#popup-background, #popup').css('display', 'block');
			$('#popup').html('<img src="${dto.filepath}">');
		});
	</script>

	<jsp:include page="/include/footer.jsp" />
</body>
</html>