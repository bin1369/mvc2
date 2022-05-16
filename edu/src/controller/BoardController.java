package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.command.BoardDelete;
import board.command.BoardDetail;
import board.command.BoardDownload;
import board.command.BoardInsert;
import board.command.BoardList;
import board.command.BoardRead;
import board.command.BoardUpdate;
import board.comment.command.CommentDelete;
import board.comment.command.CommentInsert;
import board.comment.command.CommentList;
import board.comment.command.CommentUpdate;

@WebServlet("*.bo")
@MultipartConfig
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getSession().setAttribute("category", "bo");

		String uri = request.getRequestURI();
		String context = request.getContextPath();
		uri = uri.substring(context.length());

		String view = "";
		boolean redirect = false;
		
		if (uri.equals("/list.bo")) {
			new BoardList().execute(request, response);
			view = "/board/list.jsp";

		} else if (uri.equals("/new.bo")) {
			// 신규방명록 입력 화면요청
			view = "/board/new.jsp";

		} else if (uri.equals("/insert.bo")) {
			// 신규 방명록 저장처리 요청
			new BoardInsert().execute(request, response);
			view = "list.bo";
			redirect = true;

		} else if (uri.equals("/detail.bo")) {
			// 상세화면 요청
			new BoardRead().execute(request, response);
			new BoardDetail().execute(request, response);
			view = "/board/detail.jsp";

		} else if (uri.equals("/download.bo")) {
			new BoardDownload().execute(request, response);
			return;

		} else if (uri.equals("/delete.bo")) {
			// 삭제처리 요청
			new BoardDelete().execute(request, response);
			view = "/board/redirect.jsp";

		} else if (uri.equals("/modify.bo")) {
			// 방명록 변경화면 요청
			new BoardDetail().execute(request, response);
			view = "/board/modify.jsp";

		} else if (uri.equals("/update.bo")) {
			// 방명록 변경저장처리 요청
			new BoardUpdate().execute(request, response);
			view = "/board/redirect.jsp";

		} else if (uri.equals("/comment/insert.bo")) {
			// 댓글저장처리 요청
			new CommentInsert().execute(request, response);
			return;

		} else if (uri.equals("/comment/list.bo")) {
			// 댓글목록화면 요청
			new CommentList().execute(request, response);
			view = "/board/board_comment.jsp";
			
		} else if (uri.equals("/comment/update.bo")) {
			// 댓글변경저장처리 요청
			new CommentUpdate().execute(request, response);
			return;
		} else if(uri.equals("/comment/delete.bo") ) {
			//댓글삭제처리 요청
			new CommentDelete().execute(request, response);
			return;
		}

		if (redirect)
			response.sendRedirect(view);
		else
			request.getRequestDispatcher(view).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
