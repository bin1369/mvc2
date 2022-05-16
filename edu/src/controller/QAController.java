package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import question.command.QuestionDetail;
import question.command.QuestionInsert;
import question.command.QuestionList;

@WebServlet("*.qa")
public class QAController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getSession().setAttribute("category", "qa");

		String uri = request.getRequestURI();
		String context = request.getContextPath();
		uri = uri.substring(context.length());

		String view = "";
		boolean redirect = false;

		if (uri.equals("/list.qa")) { // 질문 목록 요청
			new QuestionList().execute(request, response);
			view = "/question/list.jsp";

		} else if (uri.equals("/new.qa")) { // 질문 작성 요청
			view = "/question/new.jsp";
			
		} else if (uri.equals("/insert.qa")) { // 신규 질문 저장 요청
			new QuestionInsert().execute(request, response);
			new QuestionList().execute(request, response);
			view = "/question/list.jsp";
			
		} else if (uri.equals("/detail.qa")) { // 질문 상세보기
			new QuestionDetail().execute(request, response);
			view = "/question/detail.jsp";
		}
		
		

		if (redirect) {
			response.sendRedirect(view);
		} else {
			request.getRequestDispatcher(view).forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
