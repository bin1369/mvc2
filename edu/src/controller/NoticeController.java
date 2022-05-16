package controller;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.SHA256Util;
import member.model.MemberDAO;
import member.model.MemberDTO;
import notice.command.NoticeDelete;
import notice.command.NoticeDetail;
import notice.command.NoticeDownload;
import notice.command.NoticeInsert;
import notice.command.NoticeList;
import notice.command.NoticeRead;
import notice.command.NoticeReplyInsert;
import notice.command.NoticeUpdate;

@WebServlet("*.no")
@MultipartConfig
public class NoticeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
		
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().setAttribute("category", "no");
		
		String uri = request.getRequestURI();
		String context = request.getContextPath();
		uri = uri.substring(context.length());
		
		String view = "";
		boolean redirect = false;
		if( uri.equals("/new.no")  ){
			//신규 공지글 작성 화면 요청
			view = "/notice/new.jsp";
			
		}else if( uri.equals("/update.no")  ){
			new NoticeUpdate().execute(request, response);
			view = "detail.no?id=" + request.getParameter("id")
						+ "&curPage="+request.getParameter("curPage")
						+ "&search="+request.getParameter("search")
						+ "&keyword="+
				URLEncoder.encode(request.getParameter("keyword"),"utf-8") ;
			redirect = true;
			
		}else if( uri.equals("/modify.no")  ){
			//공지글 변경화면 요청
			new NoticeDetail().execute(request, response);
			view = "/notice/modify.jsp";
			
		}else if( uri.equals("/detail.no")  ){
			//공지글 상세화면 요청
			new NoticeRead().execute(request, response);
			new NoticeDetail().execute(request, response);
			view = "/notice/detail.jsp";
			
		}else if( uri.equals("/insert.no")  ){
			//신규 공지글 저장처리 요청
			new NoticeInsert().execute(request, response);
			view = "list.no";
			redirect = true;
			
		}else if( uri.equals("/download.no")  ){
			//첨부파일 다운로드 요청
			new NoticeDownload().execute(request, response);
			return;
			
		}else if( uri.equals("/reply_insert.no")  ){
			//답글저장처리 요청
			new NoticeReplyInsert().execute(request, response);
			redirect = true;
			view = "list.no?curPage=" + request.getParameter("curPage")
			+ "&search="+request.getParameter("search")
			+ "&keyword="+
			URLEncoder.encode(request.getParameter("keyword"),"utf-8"); 
			
		}else if( uri.equals("/reply.no")  ){
			//답글쓰기 화면 요청
			//원글의 정보를 조회
			new NoticeDetail().execute(request, response);
			view = "/notice/reply.jsp";
			
		}else if( uri.equals("/delete.no")  ){
			//공지글 삭제처리 요청
			new NoticeDelete().execute(request, response);
			view = "list.no?curPage=" + request.getParameter("curPage")
			+ "&search="+request.getParameter("search")
			+ "&keyword="+
				URLEncoder.encode(request.getParameter("keyword"),"utf-8");
			redirect = true;
			
		}else if( uri.equals("/list.no")  ){
			//임시로 관리자회원으로 로그이해 두기 - 나중에 삭제 -----
//			String salt = new MemberDAO().member_salt("hong2022");
//			String salt_pw = new SHA256Util().getEncrypt("Hong2022", salt);
//			MemberDTO member 
//				= new MemberDAO().member_login("hong2022", salt_pw);
//			request.getSession().setAttribute("loginInfo", member);	
			//-----------------------------------------
			
			new NoticeList().execute(request, response);
			//공지글목록화면 요청
			view = "/notice/list.jsp";
		}
	
		if( redirect ) {
			response.sendRedirect(view);
		}else
			request.getRequestDispatcher(view).forward(request, response);
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
