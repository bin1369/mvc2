package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import employee.command.EmployeeCode;
import employee.command.EmployeeDelete;
import employee.command.EmployeeDetail;
import employee.command.EmployeeInsert;
import employee.command.EmployeeList;
import employee.command.EmployeeUpdate;

@WebServlet("*.hr")
public class EmployeeController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().setAttribute("category", "hr");
	
		String uri = request.getRequestURI();
		String context = request.getContextPath();
		uri = uri.substring( context.length() );
		
		String view = "";
		boolean redirect = false;
		if( uri.equals("/list.hr") ) {
			//사원목록화면 요청
			new EmployeeList().execute(request, response);
			view = "/employee/list.jsp";
		}
		else if( uri.equals("/modify.hr") ) {
			//사원정보변경화면 요청
			new EmployeeDetail().execute(request, response);
			new EmployeeCode().execute(request, response);
			view = "/employee/modify.jsp";
		}
		else if( uri.equals("/update.hr") ) {
			//사원정보변경저장처리 요청
			new EmployeeUpdate().execute(request, response);
			view = "detail.hr?id=" + request.getParameter("employee_id");
			redirect = true;
		}
		else if( uri.equals("/delete.hr") ) {
			//사원정보삭제처리 요청
			new EmployeeDelete().execute(request, response);
			view = "list.hr";
			redirect=true;
		}
		else if( uri.equals("/new.hr") ) {
			//신규사원입력화면 요청
			new EmployeeCode().execute(request, response);
			view = "/employee/new.jsp";
		}
		else if( uri.equals("/insert.hr") ) {
			//신규사원저장처리 요청
			new EmployeeInsert().execute(request, response);
			view = "list.hr";
			redirect=true;
		}
		else if( uri.equals("/detail.hr") ) {
			//사원정보상세화면 요청
			new EmployeeDetail().execute(request, response);
			view = "/employee/detail.jsp";
		}
		
		if( redirect ) {
			response.sendRedirect(view);
		}else {
			request.getRequestDispatcher(view).forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
