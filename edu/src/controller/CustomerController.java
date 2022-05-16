package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import customer.command.CustomerDelete;
import customer.command.CustomerDetail;
import customer.command.CustomerInsert;
import customer.command.CustomerList;
import customer.command.CustomerUpdate;

@WebServlet("*.cu")
public class CustomerController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().setAttribute("category", "cu");
		
		String uri = request.getRequestURI(); 		//  /edu/list.cu
		String context = request.getContextPath();  //  /edu
		uri = uri.substring( context.length());  	//  /list.cu
		
		String view = "";
		boolean redirect = false;
		if( uri.equals("/list.cu") ) { 
			// 고객목록화면 요청
			new CustomerList().execute(request, response);
			
			view = "/customer/list.jsp";
		}else if( uri.equals("/new.cu") ) {
			//신규고객등록화면 요청
			view = "/customer/new.jsp";
		}else if( uri.equals("/insert.cu") ) {
			//신규고객정보저장처리 요청
			new CustomerInsert().execute(request, response);
			
			view = "list.cu";
			redirect = true;
		}else if( uri.equals("/detail.cu")) {
			//고객상세정보화면 요청
			new CustomerDetail().execute(request, response);
			view = "/customer/detail.jsp";
		}else if( uri.equals("/modify.cu")) {
			//고객정보변경화면 요청
			new CustomerDetail().execute(request, response);
			view = "/customer/modify.jsp";
		}else if( uri.equals("/update.cu")) {
			//고객정보변경저장처리 요청
			new CustomerUpdate().execute(request, response);
			view = "detail.cu?id="+request.getParameter("id");
			redirect = true;
		}else if( uri.equals("/delete.cu")) {
			new CustomerDelete().execute(request, response);
			view = "list.cu";
			redirect = true;
		}
		
		if( redirect )
			response.sendRedirect(view);
		else
			request.getRequestDispatcher(view).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
